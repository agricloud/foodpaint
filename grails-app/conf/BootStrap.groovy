import foodpaint.*

class BootStrap {

    def init = { servletContext ->

		environments {
			development {
				def item1 = new Item(name:"item1",title:"柳丁").save(failOnError: true, flush: true)
				def item2 = new Item(name:"item2",title:"橘子").save(failOnError: true, flush: true)
				def item3 = new Item(name:"item3",title:"柚子").save(failOnError: true, flush: true)

				def batch1 = new Batch(name:"batch1",item:item1).save(failOnError: true, flush: true)
				def batch2 = new Batch(name:"batch2",item:item1).save(failOnError: true, flush: true)
				def batch3 = new Batch(name:"batch3",item:item2).save(failOnError: true, flush: true)

				def workstation1 = new Workstation(name:"workstation1",title:"檢驗站01").save(failOnError: true, flush: true)
				def workstation2 = new Workstation(name:"workstation2",title:"檢驗站02").save(failOnError: true, flush: true)

				def operation1=new Operation(name:"operation1",title:"施肥").save(failOnError: true, flush: true)
				def operation2=new Operation(name:"operation2",title:"翻土").save(failOnError: true, flush: true)
				def operation3=new Operation(name:"operation3",title:"病蟲害防治").save(failOnError: true, flush: true)
				def operation4=new Operation(name:"operation4",title:"檢驗").save(failOnError: true, flush: true)
				
				def cutstomer1 = new Customer(name:"cutstomer1",title:"A先生").save(failOnError: true, flush: true)
				def cutstomer2 = new Customer(name:"cutstomer2",title:"B小姐").save(failOnError: true, flush: true)
				    			
				def order=new CustomerOrder(name:'order1').save(failOnError: true, flush: true)
				def orderDet=new CustomerOrderDet(head:order, sequence:1).save(failOnError: true, flush: true)

			}
		}

    }
    def destroy = {
    }


}
