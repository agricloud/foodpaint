package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class DocumentsController {

  //  static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
  def batchAnalyzeService
    def enumService


    def show = {

        log.debug "${controllerName}-${actionName}"

        def i18nType = grailsApplication.config.grails.i18nType


        if(documents){   

            render (contentType: 'application/json') {
                [success: true, data:documents]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false, message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }
 



 def print(index){  //傳入資料
        def i18nType = grailsApplication.config.grails.i18nType
       
     if(index==3 ) {    //資料    1 基本資料 ; 2 種子(苗)登記表 ;3栽培工作紀錄;4肥料施用紀錄;5病蟲草害防治施用紀錄;6採收記錄;7採收後處理記錄;8出貨紀錄;9肥料資材採購紀錄表;10防治資材採購紀錄表;11其他資材採購紀錄表
            def reportTitle = message(code: "栽培工作紀錄")//報表名稱   
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
            def reportDataA=[]
            def reportDataB=[]
            def reportDataC=[]
            def reportDataD=[]
            def reportDataE=[]
            def reportDataF=[]
            def reportData=[]



            def batch = Batch.findByName(params.name)
            batch.batchRoutes.each(){ batchRoute ->

                if(batchRoute.id.charAt(5)=="A"  ){//(A種苗與接穗、B農場準備、C定植、D栽培管理、E其他作業、F查核)
                def dataA = [:]
                dataA.agriculture.operation.type = "種苗與接穗"
                dataA.agriculture.operation.title = batchRoute?.operation?.title
                dataA.agriculture.batchRoute.endDate = g.formatDate(date: batchRoute?.startDate, format: 'yyyy.MM.dd')
                dataA.operation.description = batchRoute?.operation?.description
                reportDataA << dataA    
            }
                if(batchRoute.id.charAt(5)=="B"  ){//(A種苗與接穗、B農場準備、C定植、D栽培管理、E其他作業、F查核)
                def dataB = [:]
                dataB.agriculture.operation.type = "農場準備"
                dataB.agriculture.operation.title = batchRoute?.operation?.title
                dataB.agriculture.batchRoute.endDate = g.formatDate(date: batchRoute?.startDate, format: 'yyyy.MM.dd')
                dataB.operation.description = batchRoute?.operation?.description
                reportDataB << dataB             
            }
                if(batchRoute.id.charAt(5)=="C"  ){//(A種苗與接穗、B農場準備、C定植、D栽培管理、E其他作業、F查核)
                def dataC = [:]
                dataC.agriculture.operation.type = "定植"
                dataC.agriculture.operation.title = batchRoute?.operation?.title
                dataC.agriculture.batchRoute.endDate = g.formatDate(date: batchRoute?.startDate, format: 'yyyy.MM.dd')
                dataC.operation.description = batchRoute?.operation?.description
                reportDataC << dataC 
            }
                if(batchRoute.id.charAt(5)=="D"  ){//(A種苗與接穗、B農場準備、C定植、D栽培管理、E其他作業、F查核)
                def dataD = [:]
                dataD.agriculture.operation.type = "栽培管理"
                dataD.agriculture.operation.title = batchRoute?.operation?.title
                dataD.agriculture.batchRoute.endDate = g.formatDate(date: batchRoute?.startDate, format: 'yyyy.MM.dd')
                dataD.operation.description = batchRoute?.operation?.description
                reportDataD << dataD                
            }
                if(batchRoute.id.charAt(5)=="E"  ){//(A種苗與接穗、B農場準備、C定植、D栽培管理、E其他作業、F查核)
                def dataE = [:]
                dataE.agriculture.operation.type = "其他作業"
                dataE.agriculture.operation.title = batchRoute?.operation?.title
                dataE.agriculture.batchRoute.endDate = g.formatDate(date: batchRoute?.startDate, format: 'yyyy.MM.dd')
                dataE.operation.description = batchRoute?.operation?.description
                reportDataE << dataE                
            }
                if(batchRoute.id.charAt(5)=="F"  ){//(A種苗與接穗、B農場準備、C定植、D栽培管理、E其他作業、F查核)
                def dataF = [:]
                dataF.agriculture.operation.type = "查核"
                dataF.agriculture.operation.title = batchRoute?.operation?.title
                dataF.agriculture.batchRoute.endDate = g.formatDate(date: batchRoute?.startDate, format: 'yyyy.MM.dd')
                dataF.operation.description = batchRoute?.operation?.description
                reportDataF << dataF
            }
         }
                // if(reportDataA[0]==null  ){}
                // reportData.add(reportDataA);   }//[  [A] ,  [B] ,  [C]  ,  [D]  ,   [E]     ]   排序  (A種苗與接穗、B農場準備、C定植、D栽培管理、E其他作業、F查核)
                // if(reportDataB[0]==null  ){}
                // reportData.add(reportDataB);    }
                // if(reportDataC[0]==null  ){}               
                // reportData.add(reportDataC);    }
                // if(reportDataD[0]==null  ){}                     
                // reportData.add(reportDataD);    }
                // if(reportDataE[0]==null  ){}                     
                // reportData.add(reportDataE);     }
                // if(reportDataF[0]==null  ){}                     
                // reportData.add(reportDataF);     }                   

                def reportFile = jasperReportService.printPdf(params, 'BatchRouteRecordSheet.jasper', reportTitle, parameters, reportData)
                
                render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')  
            }



        if(index==4) {    //資料    1 基本資料 ; 2 種子(苗)登記表 ;3栽培工作紀錄;4肥料施用紀錄;5病蟲草害防治施用紀錄;6採收記錄;7採收後處理記錄;8出貨紀錄;9肥料資材採購紀錄表;10防治資材採購紀錄表;11其他資材採購紀錄表
            def reportTitle = message(code: "肥料施用紀錄")//報表名稱
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


            def batch = Batch.findByName(params.name)//尋找批號
            def batchFinal=batchAnalyzeService.backwardTraceToFinal(batch)

            batchFinal.batchChilds.each(){ childBatch ->
                def data = [:]
                if(childBatch.item.name.charAt(1)=="F"  ){//判斷品項編碼,區隔使用資材
               
                data.dateCreated=childBatch.item.materialSheet.materialSheetDet.dateCreated
                data.sequence=childBatch.item.materialSheet.materialSheetDet.item.sequence
                data.item.name=childBatch.item.materialSheet.materialSheetDet.item.name
                data.item.title = childBatch.item.materialSheet.materialSheetDet.item.title
                data.batch.name = childBatch.item.materialSheet.materialSheetDet.item.batch.name
                data.item.spec = childBatch.item.materialSheet.materialSheetDet.item.spec
                data.item.unit=childBatch.item.materialSheet.materialSheetDet.item.unit
                data.qty=childBatch.item.materialSheet.materialSheetDet.item.qty
                data.remark = childBatch.item.description

                    if(childBatch.item.name.substring(1,3)=="01"){//肥料別(01石灰施用 02基肥 03追肥 04次量及微量元素施用)
                         data.item.type="石灰施用"
                    }
                    if(childBatch.item.name.substring(1,3)=="02"){//肥料別(01石灰施用 02基肥 03追肥 04次量及微量元素施用)
                         data.item.type="基肥"
                    }
                    if(childBatch.item.name.substring(1,3)=="03"){//肥料別(01石灰施用 02基肥 03追肥 04次量及微量元素施用)
                         data.item.type="追肥"
                    }
                    if(childBatch.item.name.substring(1,3)=="04"){//肥料別(01石灰施用 02基肥 03追肥 04次量及微量元素施用)
                         data.item.type="次量及微量元素施用"
                    }
                }
            reportData << data
        }

            def reportFile = jasperReportService.printPdf(params, 'FertilizerRecordSheet.jasper', reportTitle, parameters, reportData)
            
            render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf') 
  
        }



        if(index==5) {    //資料    1 基本資料 ; 2 種子(苗)登記表 ;3栽培工作紀錄;4肥料施用紀錄;5病蟲草害防治施用紀錄;6採收記錄;7採收後處理記錄;8出貨紀錄;9肥料資材採購紀錄表;10防治資材採購紀錄表;11其他資材採購紀錄表
            def reportTitle = message(code: "病蟲草害防治施用紀錄")//報表名稱
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


            def batch = Batch.findByName(params.name)//尋找批號
            def batchFinal=batchAnalyzeService.backwardTraceToFinal(batch)

            batchFinal.batchChilds.each(){ childBatch ->
                def data = [:]
                if(childBatch.item.name.charAt(1) =="P"){//判斷品項編碼,區隔使用資材更新:P A030 EC 01
                    data.dateCreated=childBatch.item.materialSheet.materialSheetDet.dateCreated
                    data.item.type= childBatch.item.description//防治對象    
                    data.sequence=childBatch.item.materialSheet.materialSheetDet.item.sequence
                    data.item.name=childBatch.item.materialSheet.materialSheetDet.item.name
                    data.item.title = childBatch.item.materialSheet.materialSheetDet.item.title
                    data.batch.name = childBatch.item.materialSheet.materialSheetDet.item.batch.name
                    data.item.unit=childBatch.item.materialSheet.materialSheetDet.item.unit                    
                    data.item.spec = childBatch.item.materialSheet.materialSheetDet.item.spec
                    data.qty=childBatch.item.materialSheet.materialSheetDet.item.qty
                    data.remark = childBatch.item.description
                }
            reportData << data
        }
            def reportFile = jasperReportService.printPdf(params, 'PestRecordSheet.jasper', reportTitle, parameters, reportData)
            
            render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')  
           }





        if(index==9 ) {    //資料    1 基本資料 ; 2 種子(苗)登記表 ;3栽培工作紀錄;4肥料施用紀錄;5病蟲草害防治施用紀錄;6採收記錄;7採收後處理記錄;8出貨紀錄;9肥料資材採購紀錄表;10防治資材採購紀錄表;11其他資材採購紀錄表
            def reportTitle = message(code: "肥料採購紀錄")//報表名稱
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


            def batch = Batch.findByName(params.name)//尋找批號
            def batchFinal=batchAnalyzeService.backwardTraceToFinal(batch)

            batchFinal.batchChilds.each(){ childBatch ->
                def data = [:]
                if(childBatch.item.name.charAt(1)=="F"  ){//判斷品項編碼,區隔使用資材
               
                data.dateCreated=childBatch.item.purchaseSheet.purchaseSheetDet.dateCreated
                data.sequence=childBatch.item.purchaseSheet.purchaseSheetDet.item.sequence
                data.item.name=childBatch.item.purchaseSheet.purchaseSheetDet.item.name
                data.item.title = childBatch.item.purchaseSheet.purchaseSheetDet.item.title
                data.supplier.title=childBatch.item.purchaseSheet.supplier.title
                data.batch.name = childBatch.item.purchaseSheet.purchaseSheetDet.item.batch.name
                data.item.unit=childBatch.item.purchaseSheet.purchaseSheetDet.item.unit
                data.qty=childBatch.item.purchaseSheet.purchaseSheetDet.item.qty
                data.item.spec = childBatch.item.purchaseSheet.purchaseSheetDet.item.description
                }
            reportData << data
        }

            def reportFile = jasperReportService.printPdf(params, 'MaterialPurchaseRecordSheet.jasper', reportTitle, parameters, reportData)
            
            render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf') 
  
        }


        if(index==10 ) {    //資料    1 基本資料 ; 2 種子(苗)登記表 ;3栽培工作紀錄;4肥料施用紀錄;5病蟲草害防治施用紀錄;6採收記錄;7採收後處理記錄;8出貨紀錄;9肥料資材採購紀錄表;10防治資材採購紀錄表;11其他資材採購紀錄表
            def reportTitle = message(code: "防治資材採購紀錄")//報表名稱
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


            def batch = Batch.findByName(params.name)//尋找批號
            def batchFinal=batchAnalyzeService.backwardTraceToFinal(batch)

            batchFinal.batchChilds.each(){ childBatch ->
                def data = [:]
                if(childBatch.item.name.charAt(1)=="P"  ){//判斷品項編碼,區隔使用資材
               
                
                data.dateCreated=childBatch.item.purchaseSheet.purchaseSheetDet.dateCreated
                data.sequence=childBatch.item.purchaseSheet.purchaseSheetDet.item.sequence
                data.item.name=childBatch.item.purchaseSheet.purchaseSheetDet.item.name
                data.item.title = childBatch.item.purchaseSheet.purchaseSheetDet.item.title
                data.supplier.title=childBatch.item.purchaseSheet.supplier.title
                data.batch.name = childBatch.item.purchaseSheet.purchaseSheetDet.item.batch.name
                data.item.unit=childBatch.item.purchaseSheet.purchaseSheetDet.item.unit
                data.qty=childBatch.item.purchaseSheet.purchaseSheetDet.item.qty
                data.item.spec = childBatch.item.purchaseSheet.purchaseSheetDet.item.description
                }
            reportData << data
        }

            def reportFile = jasperReportService.printPdf(params, 'MaterialPurchaseRecordSheet.jasper', reportTitle, parameters, reportData)
            
            render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf') 
  
        }



        if(index==11 ) {    //資料    1 基本資料 ; 2 種子(苗)登記表 ;3栽培工作紀錄;4肥料施用紀錄;5病蟲草害防治施用紀錄;6採收記錄;7採收後處理記錄;8出貨紀錄;9肥料資材採購紀錄表;10防治資材採購紀錄表;11其他資材採購紀錄表
            def reportTitle = message(code: "其他資材採購紀錄")//報表名稱
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


            def batch = Batch.findByName(params.name)//尋找批號
            def batchFinal=batchAnalyzeService.backwardTraceToFinal(batch)

            batchFinal.batchChilds.each(){ childBatch ->
                def data = [:]
                if(childBatch.item.name.charAt(1)=="T"  ){//判斷品項編碼,區隔使用資材
                 
                data.dateCreated=childBatch.item.purchaseSheet.purchaseSheetDet.dateCreated
                data.sequence=childBatch.item.purchaseSheet.purchaseSheetDet.item.sequence
                data.item.name=childBatch.item.purchaseSheet.purchaseSheetDet.item.name
                data.item.title = childBatch.item.purchaseSheet.purchaseSheetDet.item.title
                data.supplier.title=childBatch.item.purchaseSheet.supplier.title
                data.batch.name = childBatch.item.purchaseSheet.purchaseSheetDet.item.batch.name
                data.item.unit=childBatch.item.purchaseSheet.purchaseSheetDet.item.unit
                data.qty=childBatch.item.purchaseSheet.purchaseSheetDet.item.qty
                data.item.spec = childBatch.item.purchaseSheet.purchaseSheetDet.item.description
                }
            reportData << data
        }

            def reportFile = jasperReportService.printPdf(params, 'MaterialPurchaseRecordSheet.jasper', reportTitle, parameters, reportData)
            
            render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf') 
  
                }
        }













    




}
