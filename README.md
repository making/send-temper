## How to install as an init.d service (System V)

    $  ./mvnw clean package -Dmaven.test.skip=true
    $ cp target/send-temper.jar /var/send-temper.jar
    $ chmod +x /var/send-temper/send-temper.jar
    $ chown <you>:<you> /var/send-temper.jar
    $ sudo ln -s /etc/init.d/send-temper /var/send-temper.jar

Build `temper`

    $ sudo apt-get install libusb-dev
    $ cd <workspace>
    $ git clone https://github.com/bitplane/temper
    $ cd temper
    $ make

Create `/var/send-temper/send-temper.conf`

    JAVA_OPTS=-Djava.security.egd=file:/dev/./urandom
    SEND_TEMPER_COMMAND=sudo,<workspace>/temper/temper
    SEND_TEMPER_TARGET=<URL to post temper data>

Configure with `visudo`

    <you>   ALL=NOPASSWD:<workspace>/temper/temper
    
Start
    
    $ sudo /etc/init.d/send-temper start
