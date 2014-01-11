package common
import org.springframework.transaction.annotation.Transactional

class DomainService {

	def messageSource 



	// def save(domainObject, params){

 //        Object[] args = [domainObject];

 //        if (!domainObject) {

 //            def msg = messageSource.getMessage("default.message.update.notfound", args, Locale.getDefault())
            
 //            return [success:false, message: msg]
            
 //        }

 //        domainObject.properties = params


 //        return save(domainObject)


	// }
    @Transactional
    def save(domainObject) {

        def success
        def errors=[:]
        def msg
        Object[] args = [domainObject]

        if (!domainObject) {

            msg = messageSource.getMessage("default.message.notfound", args, Locale.getDefault())
            
            return [success:false, message: msg]
            
        }else if (domainObject.validate() && domainObject.save()) {

            msg = messageSource.getMessage("default.message.save.success", args, Locale.getDefault())
            success=true;

        }else{

            domainObject.errors.allErrors.each{ 
                

                if(it.field == 'operation' || it.field == 'workstation' || it.field == 'item')
                    errors[it.field+".name"]=messageSource.getMessage(it, Locale.getDefault())
                else errors[it.field]=messageSource.getMessage(it, Locale.getDefault())


                log.error messageSource.getMessage(it, Locale.getDefault())
            }

            try{
                msg = messageSource.getMessage("default.message.update.failed", args, Locale.getDefault())
            }catch (e){
                msg = "更新失敗"

            }

            success=false;
        }

        return [success: success, message: msg, errors: errors]

	}
    @Transactional
    def delete(domainObject) {
    	Object[] args = [domainObject,null];
    	def msg


        if (!domainObject) {
            msg = messageSource.getMessage("default.message.notfound", args, Locale.getDefault())

            return [success:false, message: msg]
            
        }

        try {
            
            domainObject.delete()
            msg = messageSource.getMessage("default.message.delete.success", args, Locale.getDefault())
            return [success:true, message: msg]

        }
        catch (e) {
        	log.error e
            throw e
        }

    }


    // def delete(domainObject) {
    //     Object[] args = [domainObject,null];
    //     def msg


    //     if (!domainObject) {
    //         msg = messageSource.getMessage("default.not.found.message", args, Locale.getDefault())

    //         return [success:false, message: msg]
            
    //     }

    //     try {
            
    //         domainObject.delete(flush:true, failOnError:true)               
    //         msg = messageSource.getMessage("default.message.delete.success", args, Locale.getDefault())
    //         return [success:true, message: msg]

    //     }
    //     catch (e) {
    //         log.error e
    //         args[1] = e
    //         msg = messageSource.getMessage('default.message.delete.failed', args, Locale.getDefault())
    //         return [success:false, message: msg] 
    //     }

    // }
}
