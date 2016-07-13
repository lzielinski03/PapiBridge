package com.besysoft;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import stubs.InstanceInfoBean;
import stubs.InstanceInfoBeanList;
import stubs.OperationException_Exception;
import stubs.PapiWebService;
import stubs.PapiWebService_Service;
import stubs.StringListBean;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class Test {
    private static final String SECURITY_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

    public void test() {
        try {
            /////////////////// Initialize the web service client ///////////////////
            String endPoint = "http://localhost:8687/papiws/PapiWebServiceEndpoint?wsdl";
            QName qName = new QName("http://bea.com/albpm/PapiWebService", "PapiWebService");
            Service service = PapiWebService_Service.create(new URL(endPoint), qName);
            PapiWebService papiWebServicePort = service.getPort(PapiWebService.class);

            /////////////////// Configure authentication ///////////////////
            addUsernameTokenProfile(papiWebServicePort);
            addHttpBasicAuthentication(papiWebServicePort);

            /////////////////// Operate with PAPI Web Service ///////////////////
            StringListBean processIds = papiWebServicePort.processesGetIds(true);
            for (String processId : processIds.getStrings()) {
                System.out.println("\nProcess: " + processId);
                InstanceInfoBeanList instances = papiWebServicePort.processGetInstances(processId);
                for (InstanceInfoBean instance : instances.getInstances()) {
                    System.out.println("-> " + instance.getId());
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Could not connect to the web service endpoint");
            e.printStackTrace();
        } catch (OperationException_Exception e) {
            System.out.println("Could not perform the requested operation");
            e.printStackTrace();
        } catch (SOAPException e) {
            System.out.println("Could not configure Username Token Profile authentication");
            e.printStackTrace();
        }
    }

    private static void addHttpBasicAuthentication(PapiWebService papiWebServicePort) {
        Map<String, Object> request =
                ((BindingProvider) papiWebServicePort).getRequestContext();
        request.put(BindingProvider.USERNAME_PROPERTY, "asisrrmmnbsf");
        request.put(BindingProvider.PASSWORD_PROPERTY, "asisrrmmnbsf");
    }

    private static void addUsernameTokenProfile(PapiWebService papiWebServicePort)
            throws SOAPException {
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
}
