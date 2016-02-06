## How to install as an init.d service (System V)

    $  ./mvnw clean package -Dmaven.test.skip=true
    $ cp target/send-temper.jar /var/send-temper.jar
    $ chmod +x /var/send-temper/send-temper.jar
    $ chown <you>:<you> /var/send-temper.jar
    $ sudo ln -s /etc/init.d/send-temper /var/send-temper.jar

### /var/send-temper/send-temper.conf

    JAVA_OPTS=-Djava.security.egd=file:/dev/./urandom
    SEND_TEMPER_COMMAND=sudo,<PATH to temper>/temper
    SEND_TEMPER_TARGET=<URL to post temper data>

### visudo

    <you>   ALL=NOPASSWD:<PATH to temper>/temper
