package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.io.FileUtils

class StockInSheetController {

    def domainService
    def jasperService

    def index = {

        def list = StockInSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [stockInSheetInstanceList: list, stockInSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def stockInSheet=StockInSheet.get(params.id)

        if(stockInSheet){   

            render (contentType: 'application/json') {
                [success: true,data:stockInSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def stockInSheet=new StockInSheet() 

        render (contentType: 'application/json') {
            [success: true,data:stockInSheet]
        }
    }

    def save = {
        def stockInSheet=new StockInSheet(params)

        render (contentType: 'application/json') {
            domainService.save(stockInSheet)
        }
    }


    def update = {

        def stockInSheet= StockInSheet.get(params.id)
        if(stockInSheet.stockInSheetDets && params.workstation.id != stockInSheet.workstation.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'stockInSheet.stockInSheetDets.exists.workstation.not.allowed.change', args: [stockInSheet])]
            }
            return
        }
        stockInSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(stockInSheet)
        }         
    }



    def delete = {
        
        def stockInSheet = StockInSheet.get(params.id)

        def result
        try {
            result = domainService.delete(stockInSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [stockInSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){
        def  stockInSheet = [StockInSheet.get(1).properties]

        println stockInSheet.class
        println stockInSheet

        def reportDef = new JasperReportDef(name:'StockInSheet.jasper',,reportData:stockInSheet,fileFormat:JasperExportFormat.PDF_FORMAT)

        FileUtils.writeByteArrayToFile(new File("web-app/reports/StockInSheet.pdf"), jasperService.generateReport(reportDef).toByteArray())

    }
}
