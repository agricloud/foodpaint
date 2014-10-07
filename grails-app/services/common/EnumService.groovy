package common

import grails.converters.JSON

class EnumService {

	def grailsApplication
	def messageSource

	def values(enumClass){//ex :foodprint.Country

		def className = enumClass.toString().replace('class foodpaint.','')
		className = className[0].toLowerCase() + className[1..-1]

		def enumClassAry = enumClass.values()
		def result = []

		def i18nType = grailsApplication.config.grails.i18nType
		Object[] obj
        enumClassAry.each{
        	def enumClassJson = [:]
        	enumClassJson.title = messageSource.getMessage("${i18nType}.${className}.${it}.label",obj, Locale.getDefault())
        	enumClassJson.name = it.name()

        	result << enumClassJson
        }

        result
	}

	def name(enumInstance){//ex: Country country

		def className=enumInstance.class.toString().replace('class foodpaint.','')
		className = className[0].toLowerCase() + className[1..-1]
		def enumInstanceJson =[:]
		
		def i18nType = grailsApplication.config.grails.i18nType
		Object[] obj 
		enumInstanceJson.name = enumInstance.name()
		enumInstanceJson.title = messageSource.getMessage("${i18nType}.${className}.${enumInstance.name()}.label",obj, Locale.getDefault())

		enumInstanceJson
	}
}