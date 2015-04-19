package foodprint

import grails.converters.*

class CertificationTrackController {

    def batchAnalyzeService

    def index = {

        def list = Certification.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def index = {

        log.info "log.info"
        log.debug "log.debug"

        if(!params?.name || params?.name == 'null'){
            flash.message = "未指定批號！"
            render (view: 'search')
            return 
        }

        def batch = Batch.findByName(params.name)
        def product = [:]
        product.head = [:]
        product.body = [:]
        product.title = " 產品說明"


        product.head["batch.name"] = batch.name
        product.head["item.title"] = batch.item.title
        product.head["item.description"] = batch.item.description
        product.head["batch.remark"] = batch?.remark


        product.body["item.name"] = batch.item.name
        product.body["batch.manufactureDate"] = g.formatDate(date: batch.manufactureDate, format: 'yyyy.MM.dd')
        product.body["batch.expirationDate"] = g.formatDate(date: batch.expirationDate, format: 'yyyy.MM.dd')        
        product.body["item.spec"] = batch.item.spec

        
        def otherReports=[]
        def batchReportDets = BatchReportDet.findAllByBatch(batch)
        def domainReports = batchReportDets.reportParams*.report.unique()




        domainReports.each(){ report ->
            def reportMap = [:]
            reportMap.params=[]
            reportMap.title = report.title
            reportMap.reportType = report.reportType


            if(reportMap.reportType == ReportType.OTHER){
                batchReportDets.each(){ batchReportDet ->
                    if(batchReportDet.reportParams.report == report){
                        def param = [:]

                        param["param.name"] = batchReportDet.reportParams.param.name
                        param["param.title"] = batchReportDet.reportParams.param.title
                        param["param.description"] = batchReportDet.reportParams.param.description
                        param["batchReportDet.value"] = batchReportDet.value

                        reportMap.params << param
                    }
                }
                otherReports << reportMap 
            }

        }


        [batch: batch, product: product,reports: otherReports]



    }






    def nutrition = {

        if(!params?.name || params?.name == 'null'){
            flash.message = "未指定批號！"
            render (view: 'search')
            return 
        }

        def batch = Batch.findByName(params.name)

        def batchReportDets = BatchReportDet.findAllByBatch(batch)
        def domainReports = batchReportDets.reportParams*.report.unique()

        def reportMap = [:]


        reportMap.params=[]

        domainReports.each(){ report ->
            
            if(report.reportType == ReportType.NUTRITION){

                reportMap.title = report.title
                reportMap.reportType = report.reportType

                batchReportDets.each(){ batchReportDet ->
                    if(batchReportDet.reportParams.report == report){
                        def param = [:]

                        param["param.name"] = batchReportDet.reportParams.param.name
                        param["param.title"] = batchReportDet.reportParams.param.title
                        param["param.description"] = batchReportDet.reportParams.param.description
                        param["param.unit"] = batchReportDet.reportParams.param.unit
                        param["batchReportDet.value"] = batchReportDet.value

                        reportMap.params << param

                    }
                }
            }
        }

        [batch: batch, report: reportMap]



    }

    def material = {

        if(!params?.name || params?.name == 'null'){
            flash.message = "未指定批號！"
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)
        

        def batchSourceReportMap = [:]
        batchSourceReportMap.title = "原料履歷"
        batchSourceReportMap.params=[]


        def batchFinal=batchAnalyzeService.backwardTraceToFinal(batch)

        batchFinal.batchChilds.each(){ childBatch ->

            def param = [:]
            // param["batch.name"] = childBatch.name
            // param["item.name"] = childBatch.item.name
            param["item.title"] = childBatch.item.title
            param["item.spec"] = childBatch.item.spec
            param["supplier.title"] = childBatch?.supplier?.title
            param["batch.country"] = childBatch.country
            param["item.description"] = childBatch.item.description
            param["default.image"] = "/attachment/show/${childBatch.item.id}?domainName=item&fileType=jpg&site.id=${batch.site?.id}"

            batchSourceReportMap.params << param

        }

        [batch: batch, report: batchSourceReportMap]

    }
    def cultivate = {

        if(!params?.name || params?.name == 'null'){
            flash.message = "未指定批號！"
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)
        
        def batchRouteReportMap = [:]
        batchRouteReportMap.title = "栽種履歷"

        batchRouteReportMap.params=[]



        batch.batchRoutes.each(){ batchRoute ->
            def param = [:]
            // param["batchRoute.id"] = batchRoute.id
            // param["batchRoute.sequence"] = batchRoute.sequence


            
            param["agriculture.operation.title"] = batchRoute?.operation?.title

            param["agriculture.batchRoute.endDate"] = g.formatDate(date: batchRoute?.startDate, format: 'yyyy.MM.dd')

            param["agriculture.workstation.title"] = batchRoute?.workstation?.title
            param["operation.description"] = batchRoute?.operation?.description

            param["default.image"] = "/attachment/show/${batchRoute.id}?domainName=batchRoute&fileType=jpg&site.id=${batch.site?.id}"

            batchRouteReportMap.params << param

        }

        [batch: batch, report: batchRouteReportMap]

    }
    def quality = {

        if(!params?.name || params?.name == 'null'){
            flash.message = "未指定批號！"
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)
        


        def batchReportDets = BatchReportDet.findAllByBatch(batch)
        def domainReports = batchReportDets.reportParams*.report.unique()

        def reportMap = [:]


        reportMap.params=[]

        domainReports.each(){ report ->
            
            if(report.reportType == ReportType.INSPECT){

                reportMap.title = report.title
                reportMap.reportType = report.reportType

                batchReportDets.each(){ batchReportDet ->
                    if(batchReportDet.reportParams.report == report){
                        def param = [:]

                        param["inspect.param.title"] = batchReportDet.reportParams.param.title
                        param["inspect.batchReportDet.value"] = batchReportDet.value?.toFloat()

                        param["param.upper"] = batchReportDet.reportParams.param.upper?.toFloat()

                        if(param["inspect.batchReportDet.value"] <= param["param.upper"])
                            param["inspect.qualified"] = true
                        else param["inspect.qualified"] = false

                        param["inspect.dateCreated"] = batchReportDet.batchRoute.endDate.format('yyyy-MM-dd')
                        param["inspect.param.unit"] = batchReportDet.reportParams.param.unit

                        reportMap.params << param

                    }
                }
            }

        }

        [batch: batch, report: reportMap]

    }

    def operator = {

        if(!params?.name || params?.name == 'null'){
            flash.message = "未指定批號！"
            render (view: 'search')
            return
        }

        def batch = Batch.findByName(params.name)
        
        def batchRouteReportMap = [:]
        batchRouteReportMap.title = "農民履歷"//20140720-E64001

        batchRouteReportMap.params=[]



        def operators = batch.batchRoutes*.operator.unique()

        operators.each{ operator ->
            if(operator){
                def param = [:]
                
                param["default.image"] = "/attachment/show/${operator.id}?domainName=employee&fileType=jpg&site.id=${batch.site.id}"
                param["agriculture.operator.title"] = operator.title
                param["agriculture.operator.introduce"] = operator.introduce
                param["agriculture.operator.experience"] = operator.experience
                param["agriculture.operator.mainWork"] = operator.mainWork
                param["agriculture.operator.area"] = operator.area
                param["agriculture.operator.description"] = operator.description
                

                batchRouteReportMap.params << param
            }

        }

        [batch: batch, report: batchRouteReportMap]

    }

    def search = {
        render (view: 'search')
    }    

    def query = {


        def batch = Batch.findByName(params.name)

        if(batch) {
            redirect (uri: '/reports/'+batch.name)
            return 
        }else  {
            if(params.name == "")
                flash.message = "請輸入批號！"
            else flash.message = "查無批號！"
            redirect (uri: '/reports')
        }
        
    }

    def questionnaire = {
        redirect(url: "https://docs.google.com/forms/d/1nbMqNMfWWHh6Y2unpzDz5biqqLtRMGFgg9mKuCBwNCA/viewform")
        
    }


}
