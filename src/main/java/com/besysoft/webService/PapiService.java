package com.besysoft.webService;

import com.besysoft.entity.Instance;
import com.besysoft.utils.ProcessWrapper;
import stubs.*;

import java.util.List;

/**
 * Created by lzielinski on 04/08/2016.
 */
public class PapiService {

    private PapiConnection connection;

    public PapiService(String user, String pass) {
        this.connection = new PapiConnection(user, pass);
    }


    public ParticipantBean getCurrentParticipant() {
        ParticipantBean participant = null;
        try {
            participant = connection.papi.participantCurrent();
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
        return participant;
    }

    public void testVars() {
        try {
            List<VarDefinitionBean> variables = this.connection.papi.variablesGet().getVariables();
            for (VarDefinitionBean var : variables) {
                System.out.println(var.getId());
            }
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
    }

    public String getInstances() {
        ProcessWrapper wrapper = new ProcessWrapper();
        try {
            for (InstanceInfoBean instance : connection.papi.viewGetInstances("papi-bridge").getInstances()) {

                String i_Info = connection.papi.instanceGetVariable(instance.getId(), "i_Info");
                System.out.println(i_Info);

                wrapper.addInstance(
                        instance.getProcess(),
                        connection.papi.processGet(instance.getProcess()).getName(),
                        instance.getActivityName(),
                        new Instance(
                                instance.getId(),
                                instance.getDescription(),
                                instance.getState(),
                                instance.getReceptionTime(),
                                instance.getProcessDeadline()
                        )
                );
            }
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }

        return wrapper.getJSONProcess();
    }




    public void deveria(InstanceInfoBean instance) {
        try {
            ArgumentsBean bean = new ArgumentsBean();

            ArgumentsBean.Arguments arg = new ArgumentsBean.Arguments();
            ArgumentsBean.Arguments.Entry entry = new ArgumentsBean.Arguments.Entry();
            entry.setKey("a_ResultadoAnalisis");
            entry.setValue("Aprobo");
            arg.getEntry().add(entry);

            bean.setArguments(arg);

            connection.papi.processSendNotification(instance.getProcess(), "Espera", "EsperaIn", bean);
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }


    }

    // activitie and instance
    public void nus() {
        String viewId = "ComprasConformaciónLegajoRRMM";
        try {
            System.out.println("View: " + connection.papi.viewGet(viewId).getId());
            for (InstanceInfoBean instance : connection.papi.viewGetInstances(viewId).getInstances()) {
                System.out.println("\tProcess: " + instance.getProcess());
                System.out.println("\tInstance: " + instance.getId());
                System.out.println("\t" + instance.getDescription());
                System.out.println("\t\tinstanceCanBeSelected:" + connection.papi.instanceCanBeSelected(instance.getId()));
                System.out.println("\t\tinstanceCanBeProcessed:" + connection.papi.instanceCanBeProcessed(instance.getId()));
                System.out.println("\t\tinstanceCanBeSent:" + connection.papi.instanceCanBeSent(instance.getId()));
                System.out.println();

                ArgumentsBean bean = new ArgumentsBean();

                ArgumentsBean.Arguments arg = new ArgumentsBean.Arguments();
                ArgumentsBean.Arguments.Entry entry = new ArgumentsBean.Arguments.Entry();
                entry.setKey("a_ResultadoAnalisis");
                entry.setValue("Aprobo");
                arg.getEntry().add(entry);

                bean.setArguments(arg);


                connection.papi.processSendNotification(instance.getProcess(), "Espera", "EsperaIn", bean);
                System.out.println("\tVisible activities from Process: " + connection.papi.processGetVisibleActivities(instance.getProcess()).getActivities().size());
                for (ActivityBean activitie : connection.papi.processGetVisibleActivities(instance.getProcess()).getActivities()) {
                    System.out.println("\t\t" + activitie.getName());
                    System.out.println("\t\tTasks:");
                    for (TaskBean task : activitie.getTasks()) {
                        System.out.println("\t\t\t" + task.getName());
                    }
                }
                System.out.println("----------------------------------------------------------");
            }
        } catch (OperationException_Exception e) {
            e.printStackTrace();
        }
    }
}
