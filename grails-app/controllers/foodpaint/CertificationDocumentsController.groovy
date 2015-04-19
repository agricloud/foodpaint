package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class CertificationDocumentsController{

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def i18nType = grailsApplication.config.grails.i18nType

        def certification = Certification.get(params.saleReturnSheet.id)

        if(certification){
            
            def certificationDocuments= CertificationDocuments.findAllBycertification(certification)

            render (contentType: 'application/json') {
               [success: true, data:certification, total: certificationDocuments.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }
        }
        
    }






    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def certificationDocuments = CertificationDocuments.get(params.id);


        if(certificationDocuments){   

            render (contentType: 'application/json') {
                [success: true,data:certificationDocuments]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }


    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

        if(params.certification.id){

            def certificationDocuments= new CertificationDocuments(params)

            certificationDocuments.typeName = certificationDocuments.certification.typeName
            certificationDocuments.name = certificationDocuments.certification.name

            if(certificationDocuments.certification.certificationDocumentss)
                certificationDocuments.sequence = certificationDocuments.certification.certificationDocumentss*.sequence.max()+1
            else certificationDocuments.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:certificationDocuments]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.certificationDocuments.message.create.failed")]
            }            
        }

    }

    @Transactional
    def save(){

        def i18nType = grailsApplication.config.grails.i18nType

        def certificationDocuments=new CertificationDocuments(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(certificationDocuments.typeName != certificationDocuments.certification.typeName || certificationDocuments.name != certificationDocuments.certification.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sheet.typeName.name.not.equal")]
            }
            return
        }

    //     if(certificationDocuments.item != saleReturnSheetDet.saleSheetDet.item || saleReturnSheetDet.batch != saleReturnSheetDet.saleSheetDet.batch){
    //         render (contentType: 'application/json') {
    //             [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal", args:saleReturnSheetDet)]
    //         }
    //         return
    //     }

    //     if(saleReturnSheetDet.customerOrderDet != saleReturnSheetDet.saleSheetDet.customerOrderDet){
    //         render (contentType: 'application/json') {
    //                 [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.customerOrderDet.saleSheetDet.customerOrderDet.not.equal", args: [saleReturnSheetDet])]
    //         }
    //         return
    //     }

    //     def inventoryReplenishResult = inventoryDetailService.replenish(params,saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty, saleReturnSheetDet.dateCreated)
        
    //     if(inventoryReplenishResult.success){
    //         render (contentType: 'application/json') {
    //             domainService.save(saleReturnSheetDet)
    //         }
    //     }
    //     else{
    //         render (contentType: 'application/json') {
    //             inventoryReplenishResult
    //         } 
    //     }
        }




  def update(){
        def certificationDocuments = Certification.get(params.id)
        certificationDocuments.properties=params
        render (contentType: 'application/json') {
            domainService.save(certificationDocuments)
        }         
    }


    @Transactional
        def delete(){

        def i18nType = grailsApplication.config.grails.i18nType
        
        def certificationDocuments = certification.get(params.id)
        def result
        try {
            
            result = domainService.delete(certificationDocuments)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [certificationDocuments, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
