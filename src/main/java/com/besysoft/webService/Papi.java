package com.besysoft.webService;

import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import stubs.*;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class Papi {

    private static final String SECURITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    private PapiWebService papiWebServicePort;
    private String username;
    private String password;

    public Papi(String user, String pass) {
        this.username = user;
        this.password = pass;
        /////////////////// Initialize the web service client ///////////////////
        try {
            String endPoint = "http://localhost:8687/papiws/PapiWebServiceEndpoint?wsdl";
            QName qName = new QName("http://bea.com/albpm/PapiWebService", "PapiWebService");
            Service service = null;
            service = PapiWebService_Service.create(new URL(endPoint), qName);
            papiWebServicePort = service.getPort(PapiWebService.class);

            /////////////////// Configure authentication ///////////////////
            addUsernameTokenProfile(papiWebServicePort);
            addHttpBasicAuthentication(papiWebServicePort);

        } catch (MalformedURLException e) {
            System.out.println("Could not connect to the web service endpoint");
            e.printStackTrace();
        } catch (SOAPException e) {
            System.out.println("Could not configure Username Token Profile authentication");
            e.printStackTrace();
        }
    }

    private  void addHttpBasicAuthentication(PapiWebService papiWebServicePort) {
        Map<String, Object> request = ((BindingProvider) papiWebServicePort).getRequestContext();
        request.put(BindingProvider.USERNAME_PROPERTY, this.username);
        request.put(BindingProvider.PASSWORD_PROPERTY, this.password);
    }

    private static void addUsernameTokenProfile(PapiWebService papiWebServicePort) throws SOAPException {
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        QName securityQName = new QName(SECURITY_NAMESPACE, "Security");
        SOAPElement security = soapFactory.createElement(securityQName);
        QName tokenQName = new QName(SECURITY_NAMESPACE, "UsernameToken");
        SOAPElement token = soapFactory.createElement(tokenQName);
        QName userQName = new QName(SECURITY_NAMESPACE, "Username");
        SOAPElement username = soapFactory.createElement(userQName);
        username.addTextNode("test");
        QName passwordQName = new QName(SECURITY_NAMESPACE, "Password");
        SOAPElement password = soapFactory.createElement(passwordQName);
        password.addTextNode("test");
        token.addChildElement(username);
        token.addChildElement(password);
        security.addChildElement(token);
        Header header = Headers.create(security);
        ((WSBindingProvider) papiWebServicePort).setOutboundHeaders(header);
    }
    
    public void login() {
        try {
            ParticipantBean participantBean = papiWebServicePort.participantCurrent();
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
    }

    public StringListBean processesGetIds() {
        StringListBean processIds = null;
        try {
            processIds = papiWebServicePort.processesGetIds(true);
        } catch (OperationException_Exception e) {
            System.out.println("Could not perform the requested operation");
            e.printStackTrace();
        }
        return processIds;
    }

    public ProcessBeanList processesGet() {
        ProcessBeanList processes = null;
        try {
            processes = papiWebServicePort.processesGet(true, true);

        } catch (OperationException_Exception e) {
            System.out.println("Could not perform the requested operation");
            e.printStackTrace();
        }
        return processes;
    }

    public ParticipantBean getCurrentParticipant() {
        ParticipantBean participant = null;
        try {
            participant = papiWebServicePort.participantCurrent();
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return participant;
    }

    public InstanceInfoBean getInstanceInActivity() {
        InstanceInfoBean conformarLegajo = null;
        try {
            conformarLegajo = papiWebServicePort.processGetInstanceInActivity("ConformarLegajo", "");
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return conformarLegajo;
    }

    public InstanceInfoBeanList processGetInstances() {
        InstanceInfoBeanList instanceInfoBeanList = null;
        try {
            instanceInfoBeanList = papiWebServicePort.processGetInstances("/ComprasConformaciónLegajoRRMM#Default-1.0");
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return instanceInfoBeanList;
    }
}
