package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class MaterialSheetController {

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

    def index = {

        def list = MaterialSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def indexByWorkstationOrSupplier = {

        def workstation = Workstation.get(params.workstation.id)
        def supplier = Supplier.get(params.supplier.id)
        def list = MaterialSheet.findAllByWorkstationAndSupplier(workstation,supplier)

        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }
    }
    
    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def materialSheet=MaterialSheet.get(params.id)

        if(materialSheet){   

            render (contentType: 'application/json') {
                [success: true,data:materialSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }

    def create = {

        def materialSheet=new MaterialSheet()

        render (contentType: 'application/json') {
            [success: true,data:materialSheet]
        }
    }

    def save = {

        def i18nType = grailsApplication.config.grails.i18nType

        def materialSheet=new MaterialSheet(params)
        if((materialSheet.workstation && materialSheet.supplier)||(!materialSheet.workstation && !materialSheet.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.materialSheet.workstation.supplier.should.exist.one", args: [materialSheet])]
            }
            return
        }
        render (contentType: 'application/json') {
            domainService.save(materialSheet)
        }
    }


    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def materialSheet= new MaterialSheet(params)
        
        if((materialSheet.workstation && materialSheet.supplier)||(!materialSheet.workstation && !materialSheet.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.materialSheet.workstation.supplier.should.exist.one", args: [materialSheet])]
            }
            return
        }
        materialSheet= MaterialSheet.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != materialSheet.typeName || params.name != materialSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        //工作站/供應商一旦建立不允許變更
        if(materialSheet.materialSheetDets && ((params.workstation?.id && params.workstation.id.toLong() != materialSheet.workstation?.id) || (params.supplier?.id &&params.supplier.id.toLong() != materialSheet.supplier?.id))){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.materialSheet.materialSheetDets.exists.workstationOrSupplier.not.allowed.change", args: [materialSheet])]
            }
            return
        }

        materialSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(materialSheet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def materialSheet = MaterialSheet.get(params.id)

        def result
        try {
            result = domainService.delete(materialSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [materialSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def reportTitle = message(code: "${i18nType}.materialSheet.report.title.label")
        
        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('sequence');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        //設定額外傳入參數
        def parameters=[:]
        parameters["SORT_FIELDS"]=sortList
        //設定準備傳入的資料
        def reportData=[]
        def materialSheet = MaterialSheet.get(params.id)
        materialSheet.materialSheetDets.each{ materialSheetDet ->
            def data=[:]
            data.dateCreated=materialSheet.dateCreated
            data.lastUpdated=materialSheet.lastUpdated
            data.typeName=materialSheet.typeName
            data.name=materialSheet.name
            data.workstation=materialSheet.workstation
            data.supplier=materialSheet.supplier
            data.sequence=materialSheetDet.sequence
            data.item=materialSheetDet.item
            data.warehouse=materialSheetDet.warehouse
            data.warehouseLocation=materialSheetDet.warehouseLocation
            data.batch=materialSheetDet.batch
            data.qty=materialSheetDet.qty
            data.manufactureOrder=materialSheetDet.manufactureOrder
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'MaterialSheet.jasper', reportTitle, parameters, reportData)
        
        render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')
    }
}
