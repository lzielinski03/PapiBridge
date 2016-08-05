package com.besysoft.utils;

import com.besysoft.entity.Activity;
import com.besysoft.entity.Instance;
import com.besysoft.entity.Process;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzielinski on 04/08/2016.
 */
public class ProcessWrapper {

    private List<Process> processes = new ArrayList<>();

    public void addInstance(String processId, String processName, String activityName, Instance instance) {
        if (this.processes.size() == 0)
            this.processes.add(new Process(processId, processName, new Activity(activityName, instance)));  // new process
        else {
            // find process
            boolean isProcessAdded = false;

            for (Process process : processes) {
                // add process
                if (processId.equals(process.id)) {
                    if (process.activities.size() == 0)
                        process.activities.add(new Activity(activityName, instance)); // new activity
                    else {
                        // find activity
                        boolean isActivityAdded = false;

                        for (Activity activity : process.activities) {
                            if (activityName.equals(activity.name)){
                                activity.instances.add(instance);
                                isActivityAdded = true;
                            }
                        }
                        if (!isActivityAdded)
                            process.activities.add(new Activity(activityName, instance));
                    }

                    //process.activities.get(0).instances.add(instance);
                    isProcessAdded = true;
                }
            }
            // not find process, add new
            if (!isProcessAdded)
                this.processes.add(new Process(processId, processName, new Activity(activityName, instance)));
            //                this.processes.add(new Process(processId, processName, instance));

        }
    }

    public String getJSONProcess() {
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(this.processes);
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this.processes));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
