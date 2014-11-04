package foodpaint

import grails.converters.JSON

//generate jasperreport
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.io.FileUtils

import org.codehaus.groovy.grails.web.context.ServletContextHolder

class JasperReportService{

    def dateService
    def jasperService

    def printPdf = { def params, jasperFile, reportTitle, parameters, reportData ->

        def site
        if(params.site.id && params.site.id!="null")
            site = Site.get(params.site.id)
        
        parameters["site.title"]=site?.title
        parameters["report.title"]=reportTitle
        parameters["REPORT_TIME_ZONE"]=dateService.getTimeZone()
        parameters["SUBREPORT_DIR"]=ServletContextHolder.servletContext.getResource("/reports/")
        parameters["IMAGE_DIR"]=ServletContextHolder.servletContext.getResource("/images/")

        def reportDef = new JasperReportDef(name:jasperFile,parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName = dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"

        def file = new File(ServletContextHolder.servletContext.getRealPath("/reportFiles/")+"${site?'/'+site.name:''}/${fileName}")

        FileUtils.writeByteArrayToFile(file, jasperService.generateReport(reportDef).toByteArray())

        return file
    }

}
