package com.example.sender_point_to_point;

import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.log4j.BasicConfigurator;



import org.apache.log4j.BasicConfigurator;

import java.util.Properties;

public class HelloController {
    @FXML
    private TextField txtMa;
    @FXML
    private TextField txtCMND;
    @FXML
    private TextField txtHoTen;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private Button btnLuu;

public void SenData(ActionEvent actionEvent) throws Exception {
    //config environment for JMS
    BasicConfigurator.configure();
//config environment for JNDI
    Properties settings=new Properties();
    settings.setProperty(Context.INITIAL_CONTEXT_FACTORY,
            "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
    settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
//create context
    Context ctx=new InitialContext(settings);
//lookup JMS connection factory
    ConnectionFactory factory=
            (ConnectionFactory)ctx.lookup("ConnectionFactory");
//lookup destination. (If not exist-->ActiveMQ create once)
    Destination destination=
            (Destination) ctx.lookup("dynamicQueues/thanthidet");
//get connection using credential
    Connection con=factory.createConnection("admin","admin");
//connect to MOM
    con.start();
//create session
    Session session=con.createSession(
            /*transaction*/false,
            /*ACK*/Session.AUTO_ACKNOWLEDGE
    );
//create producer
    MessageProducer producer = session.createProducer(destination);
//create text message
//    Message msg=session.createTextMessage("hello mesage from ActiveMQ");


    Person person=new Person(txtMa.getText(),txtCMND.getText(),txtHoTen.getText(),txtDiaChi.getText());
    // convert oject to xml string
    StringWriter sw = new StringWriter();
    JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
    jaxbMarshaller.marshal(person, sw);
    String xmlString = sw.toString();
    Message msg=session.createTextMessage(xmlString);

    producer.send(msg);
//    Person p=new Person(1001, "Thân Thị Đẹt", new Date());
//    String xml=new XMLConvert<Person>(p).object2XML(p);
//    msg=session.createTextMessage(xml);
//    producer.send(msg);
//shutdown connection
    session.close();con.close();
    System.out.println("Finished...");


}
}