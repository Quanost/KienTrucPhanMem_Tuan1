package com.example.receiver_point_to_point;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.StringReader;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.BasicConfigurator;


public class HelloController {
    @FXML
    private Button btnCapNhat;

    @FXML
    private Button btnGoiKham;

    @FXML
    private TableColumn<Person, String> ma;

    @FXML
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, String> ten;

    @FXML
    private TextField txtCMND;

    @FXML
    private TextField txtDiaChi;

    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtMa;

    @FXML
    private TextField txtNoiDungKham;

    @FXML
    private  ObservableList<Person> list;


    public void initialize() throws Exception {
//thiết lập môi trường cho JMS
        BasicConfigurator.configure();
//thiết lập môi trường cho JJNDI
        Properties settings=new Properties();
        settings.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        settings.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
//tạo context
        Context ctx=new InitialContext(settings);
//lookup JMS connection factory
        Object obj=ctx.lookup("ConnectionFactory");
        ConnectionFactory factory=(ConnectionFactory)obj;
//lookup destination
        Destination destination
                =(Destination) ctx.lookup("dynamicQueues/thanthidet");
//tạo connection
        Connection con=factory.createConnection("admin","admin");
//nối đến MOM
        con.start();
//tạo session
        Session session=con.createSession(
                /*transaction*/false,
                /*ACK*/Session.CLIENT_ACKNOWLEDGE
        );
//tạo consumer
        MessageConsumer receiver = session.createConsumer(destination);
//blocked-method for receiving message - sync
//receiver.receive();
//Cho receiver lắng nghe trên queue, chừng có message thì notify - async
        System.out.println("Tý was listened on queue...");
        receiver.setMessageListener(new MessageListener() {
            @Override
//có message đến queue, phương thức này được thực thi
            public void onMessage(Message msg) {//msg là message nhận được
                try {
                    if(msg instanceof TextMessage){
                        TextMessage tm=(TextMessage)msg;
                        String txt=tm.getText();
                        System.out.println("Nhận được "+txt);
                        msg.acknowledge();//gửi tín hiệu ack


                        // convert mxl string to Oject java
                        StringReader sr = new StringReader(txt);
                        JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        Person response = (Person) unmarshaller.unmarshal(sr);


                        //add oject table view
                         list= table.getItems();
                        list.add(response);
                        ma.setCellValueFactory(new PropertyValueFactory<Person, String>("maSo"));
                        ten.setCellValueFactory(new PropertyValueFactory<Person, String>("hoTen"));
                        table.setItems(list);

                        System.out.println("Oject nhan duoc la:"+list);
                    }
                    else if(msg instanceof ObjectMessage){
                        ObjectMessage om=(ObjectMessage)msg;
                        System.out.println(om);
                    }
//others message type....
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void CapNhat(ActionEvent e){
        Person person=  table.getSelectionModel().getSelectedItem();
        list.remove(person);
    }

    public void GoiKham(ActionEvent e){
        Person person=  table.getSelectionModel().getSelectedItem();
        txtMa.setText(person.getMaSo());
        txtCMND.setText(person.getcMND());
        txtHoTen.setText(person.getHoTen());
        txtDiaChi.setText(person.getDiaChi());
    }
}