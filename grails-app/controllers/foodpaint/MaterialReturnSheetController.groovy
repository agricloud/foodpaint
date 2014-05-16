package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
//generate irepoet
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.io.FileUtils
//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class MaterialReturnSheetController {

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = MaterialReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {
        def materialReturnSheet=MaterialReturnSheet.get(params.id)

        if(materialReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:materialReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def materialReturnSheet=new MaterialReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:materialReturnSheet]
        }
    }

    def save = {
        def materialReturnSheet=new MaterialReturnSheet(params)
        if((materialReturnSheet.workstation && materialReturnSheet.supplier)||(!materialReturnSheet.workstation && !materialReturnSheet.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheet.workstation.supplier.should.exist.one', args: [materialReturnSheet])]
            }
            return
        }
        render (contentType: 'application/json') {
            domainService.save(materialReturnSheet)
        }
    }


    def update = {

        def materialReturnSheet= MaterialReturnSheet.get(params.id)
        if(materialReturnSheet.materialReturnSheetDets && (params.workstation.id.toLong() != materialReturnSheet.workstation?.id || params.supplier.id.toLong() != materialReturnSheet.supplier?.id)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheet.materialReturnSheetDets.exists.workstationOrSupplier.not.allowed.change', args: [materialReturnSheet])]
            }
            return
        }
        materialReturnSheet.properties = params
        if((materialReturnSheet.workstation && materialReturnSheet.supplier)||(!materialReturnSheet.workstation && !materialReturnSheet.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheet.workstation.supplier.should.exist.one', args: [materialReturnSheet])]
            }
            return
        }
        render (contentType: 'application/json') {
            domainService.save(materialReturnSheet)
        }         
    }



    def delete = {
        
        def materialReturnSheet = MaterialReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(materialReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [materialReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){
        def site
        if(params.site.id && params.site.id!="null")
            site = Site.get(params.site.id)

        def reportTitle = message(code: 'materialReturnSheet.report.title.label')
        
        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('sequence');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        //設定額外傳入參數
        def parameters=[:]
        parameters["site.title"]=site?.title
        parameters["report.title"]=reportTitle
        parameters["REPORT_TIME_ZONE"]=dateService.getTimeZone()
        parameters["SORT_FIELDS"]=sortList
        //設定準備傳入的資料
        def reportData=[]
        def materialReturnSheet = MaterialReturnSheet.get(params.id)
        materialReturnSheet.materialReturnSheetDets.each{ materialReturnSheetDet ->
            def data=[:]
            data.dateCreated=materialReturnSheet.dateCreated
            data.lastUpdated=materialReturnSheet.lastUpdated
            data.typeName=materialReturnSheet.typeName
            data.name=materialReturnSheet.name
            data.workstation=materialReturnSheet.workstation
            data.supplier=materialReturnSheet.supplier
            data.sequence=materialReturnSheetDet.sequence
            data.item=materialReturnSheetDet.item
            data.warehouse=materialReturnSheetDet.warehouse
            data.warehouseLocation=materialReturnSheetDet.warehouseLocation
            data.batch=materialReturnSheetDet.batch
            data.qty=materialReturnSheetDet.qty
            data.materialSheetDet=materialReturnSheetDet.materialSheetDet
            data.manufactureOrder=materialReturnSheetDet.manufactureOrder
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'MaterialReturnSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
