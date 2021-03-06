
package fr.afcepf.al24.bank.bpel;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BPELBankPortType", targetNamespace = "http://bpel.bank.al24.afcepf.fr")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    fr.afcepf.al24.bank.bpel.ObjectFactory.class,
    fr.afcepf.al24.bank.transaction.ObjectFactory.class,
    fr.afcepf.al24.bank.validation.ObjectFactory.class
})
public interface BPELBankPortType {


    /**
     * 
     * @param payload
     * @return
     *     returns fr.afcepf.al24.bank.bpel.BPELBankResponse
     */
    @WebMethod(action = "http://bpel.bank.al24.afcepf.fr/process")
    @WebResult(name = "BPELBankResponse", targetNamespace = "http://bpel.bank.al24.afcepf.fr", partName = "payload")
    public BPELBankResponse process(
        @WebParam(name = "BPELBankRequest", targetNamespace = "http://bpel.bank.al24.afcepf.fr", partName = "payload")
        BPELBankRequest payload);

}
