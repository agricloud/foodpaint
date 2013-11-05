#remote_addr=192.168.0.107
remote_addr=192.168.1.18
remote_user=demo


# server:
# #	export GRAILS_OPTS="-XX:MaxPermSize=1024m -Xmx1024M -server"
# 	grails run-app


# commit:
# 	git commit . -m 'development update'
# 	git push

# update:
# 	git pull



# submoduleInstall:
# 	git submodule init
# 	git submodule update

#upload:
# 	scp target/extrails.war ${remote_user}@${remote_addr}:~/extrails/target/ 
# 	scp ~/.grails/extrails-config.groovy ${remote_user}@${remote_addr}:~/.grails/


# deploy:
# 	cp ~/.grails/extrails-config.groovy /usr/share/tomcat7/.grails/
# 	rm -rf /var/lib/tomcat7/webapps/ROOT.war
# 	rm -rf /var/lib/tomcat7/webapps/ROOT
# 	cp target/extrails.war /var/lib/tomcat7/webapps/ROOT.war
# 	service tomcat7 restart





remote-dbinit:
	sudo apt-get install mysql-server libapache2-mod-auth-mysql php5-mysql phpmyadmin libapache2-mod-php5
	mysql -u root -p
	CREATE DATABASE foodpaint DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
	create user 'foodpaint'@'localhost' identified by 'foodpaint';
	grant all on *.* to 'foodpaint'@'localhost';
	



# remote-log:
# 	ssh -t ${remote_user}@${remote_addr} 'cd extrails && sudo make log'




# syncdb:
# 	mysqldump -h codecanaan.com -usynconly -p contpub | mysql -h localhost -ucontpub -pcontpub contpub

# services:
# 	mysqld_safe5 &
# 	rabbitmq-server &
remote-init:
	ssh -t ${remote_user}@${remote_addr} 'sudo mkdir -p /usr/share/tomcat6/.grails \
	&& sudo chgrp -R tomcat6 /usr/share/tomcat6 \
	&& sudo chmod -R 770 /usr/share/tomcat6'

clean:
	grails clean


war:
	grails war


deployWar:
	scp ~/.grails/foodpaint-config.groovy ${remote_user}@${remote_addr}:~/
	scp target/foodpaint.war ${remote_user}@${remote_addr}:~/

	ssh -t ${remote_user}@${remote_addr} \
	'cd ~/ \
	&& sudo rm -rf /var/lib/tomcat6/webapps/foodpaint \
	&& sudo cp foodpaint.war /var/lib/tomcat6/webapps/ \
	&& sudo cp foodpaint-config.groovy /usr/share/tomcat6/.grails/ \
	&& sudo service tomcat6 restart'

		

done:
	make clean war deployWar

log:
	ssh -t ${remote_user}@${remote_addr} 'sudo tail -f /var/lib/tomcat6/logs/catalina.out'

install:
	make remote-init done


loglink:
	- mkdir ~/Library/Logs/foodpaint
	- touch target/development.log
	- touch target/test.log
	- touch target/grails.log
	- touch target/root.log
	- touch target/stacktrace.log
	- ln ~/projects/foodpaint/target/development.log ~/Library/Logs/foodpaint/development.log
	- ln ~/projects/foodpaint/target/grails.log ~/Library/Logs/foodpaint/grails.log
	- ln ~/projects/foodpaint/target/root.log ~/Library/Logs/foodpaint/root.log
	- ln ~/projects/foodpaint/target/stacktrace.log ~/Library/Logs/foodpaint/stacktrace.log
	- ln ~/projects/foodpaint/target/test.log ~/Library/Logs/foodpaint/test.log


# remote-deploy:
# 	ssh -t ${remote_user}@${remote_addr} 'cd extrails && make update && sudo make deploy'



