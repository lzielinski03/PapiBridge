package com.besysoft.webService;

import com.besysoft.entity.Instance;
import com.besysoft.entity.Process;
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
import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class PapiConnection {

    private static final String SECURITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    public PapiWebService papi;
    private String username;
    private String password;

    public PapiConnection(String user, String pass) throws WebServiceException {
        this.username = user;
        this.password = pass;
        /////////////////// Initialize the web service client ///////////////////
        try {
            String endPoint = "http://192.168.1.42:8070/papiws/PapiWebServiceEndpoint?wsdl";
            QName qName = new QName("http://bea.com/albpm/PapiWebService", "PapiWebService");
            Service service = null;
            service = PapiWebService_Service.create(new URL(endPoint), qName);
            papi = service.getPort(PapiWebService.class);

            /////////////////// Configure authentication ///////////////////
            addUsernameTokenProfile(papi);
            addHttpBasicAuthentication(papi);

        } catch (WebServiceException e) {
            System.out.println("PapiWebService is not a valid service.");
        } catch (MalformedURLException e) {
            System.out.println("Could not connect to the web service endpoint");
            e.printStackTrace();
        } catch (SOAPException e) {
            System.out.println("Could not configure Username Token Profile authentication");
            e.printStackTrace();
        }
    }

    private void addHttpBasicAuthentication(PapiWebService papiWebServicePort) {
        Map<String, Object> request = ((BindingProvider) papiWebServicePort).getRequestContext();
        request.put(BindingProvider.USERNAME_PROPERTY, this.username);
        request.put(BindingProvider.PASSWORD_PROPERTY, this.password);
    }

    private void addUsernameTokenProfile(PapiWebService papiWebServicePort) throws SOAPException {
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        QName securityQName = new QName(SECURITY_NAMESPACE, "Security");
        SOAPElement security = soapFactory.createElement(securityQName);
        QName tokenQName = new QName(SECURITY_NAMESPACE, "UsernameToken");
        SOAPElement token = soapFactory.createElement(tokenQName);
        QName userQName = new QName(SECURITY_NAMESPACE, "Username");
        SOAPElement username = soapFactory.createElement(userQName);
        //username.addTextNode("test");
        username.addTextNode(this.username);
        QName passwordQName = new QName(SECURITY_NAMESPACE, "Password");
        SOAPElement password = soapFactory.createElement(passwordQName);
        password.addTextNode(this.password);
        token.addChildElement(username);
        token.addChildElement(password);
        security.addChildElement(token);
        Header header = Headers.create(security);
        ((WSBindingProvider) papiWebServicePort).setOutboundHeaders(header);
    }
}