<template>
  <div v-if="this.$store?.state?.showTasks">
    <div class="zone">
      <div class="flex justify-start bg-white">
        <v-tabs
            class="fill-height"
            color="#1e90ff"
            v-model="currentStage">
          <v-tab class="menu_button2"
                 prepend-icon="mdi-notification-clear-all"
                 value="ALL">
            All Tasks
          </v-tab>
          <v-tab class="menu_button2"
                 prepend-icon="mdi-progress-helper"
                 value="TO_DO">
            To Do
          </v-tab>
          <v-tab class="menu_button2"
                 prepend-icon="mdi-progress-check"
                 value="IN_PROGRESS">
            In Progress
          </v-tab>
          <v-tab class="menu_button2"
                 prepend-icon="mdi-check-all"
                 value="COMPLETED">
            Completed
          </v-tab>
        </v-tabs>
        <v-spacer></v-spacer>

        <v-spacer></v-spacer>
        <v-tabs>

          <v-text-field
              class="ml-5 border-r-2 border-l-2 mr-20"
              width="300px"
              prepend-inner-icon="mdi-magnify"
              placeholder="Search By Name"
              v-model="searchBar"
          ></v-text-field>
          <v-tab
              prepend-icon="mdi-file-document-plus-outline"
              class="menu_button"
              v-on:click="addTask">
            New Task
          </v-tab>
        </v-tabs>
      </div>
      <div class="tasks">

        <v-card class="task"
                v-for="task in this.$store?.state?.tasks.filter(t => (t.stage === currentStage || currentStage==='ALL') && (searchBar === '' || searchBar === undefined || t.name.toLowerCase().includes(searchBar.toLowerCase())))"
                :key="task.id">
          <v-card-item>
            <v-card-title class="text-body-2 d-flex align-center ">

              <div class="text-h6 "><a class="font-bold">
                <v-icon
                    color="#1e90ff"
                    class="mb-1"
                    icon="mdi-checkbox-blank-outline"
                    start
                ></v-icon>
                {{ task.name.length > 28 ? task.name.slice(0, 28) + '..' : task.name }}
              </a></div>
              <v-spacer></v-spacer>

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="green"
                  size="small"
                  text="Low"
                  v-if="task.priority === 'LOW'"
                  variant="outlined"
              ></v-chip>

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="orange"
                  size="small"
                  text="Medium"
                  v-if="task.priority === 'MEDIUM'"
                  variant="outlined"
              ></v-chip>

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="red"
                  size="small"
                  text="High"
                  v-if="task.priority === 'HIGH'"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium-emphasis"
                  prepend-icon="mdi-eye"
                  color="#1e90ff"
                  size="small"
                  text="OPEN"
                  variant="outlined"
                  @click="openTask(task)"
              ></v-chip>

            </v-card-title>

            <div class="py-2">
              <div class="text-h6 border-b-2"><a class="font-bold"></a></div>

              <div class="font-weight-light text-medium-emphasis">
                {{ task.description.length > 450 ? task.description.slice(0, 450) + '...' : task.description }}
              </div>
            </div>
          </v-card-item>
          <v-card-item>
            <v-chip
                class="ms-2 mb-2 text-medium"
                prepend-icon="mdi-decagram"
                color="#1e90ff"
                size="small"
                :text="'Difficulty: ' + task.difficulty"
                variant="outlined"
            ></v-chip>
            <v-chip
                class="ms-2 mb-2 text-medium"
                color="#1e90ff"
                size="small"
                :text="task.progress +'% DONE'"
                v-if="currentStage === 'IN_PROGRESS'"
                variant="outlined"
            ></v-chip>

            <v-chip
                class="ms-2 mb-2 text-medium"
                prepend-icon="mdi-text-box-multiple-outline"
                color="#1e90ff"
                size="small"
                :text="task.project.name"
                v-if="task.projectId!==null"
                variant="outlined"
            ></v-chip>
            <v-chip
                class="ms-2 mb-2 text-medium"
                prepend-icon="mdi-clipboard-text-off-outline"
                color="grey"
                size="small"
                text="No Related Project"
                v-if="task.projectId===null"
                variant="outlined"
            ></v-chip>


            <v-chip
                class="ms-2 mb-2 text-medium"
                prepend-icon="mdi-account"
                color="#1e90ff"
                size="small"
                :text="task.user.firstName+' '+task.user.lastName"
                v-if="task.userId!==null && task.user!==undefined"
                variant="outlined"
            ></v-chip>
            <v-chip
                class="ms-2 mb-2 text-medium"
                prepend-icon="mdi-account"
                color="grey"
                size="small"
                text="No Assigned User"
                v-if="task.userId===null"
                variant="outlined"
            ></v-chip>

            <v-chip
                v-if="currentStage==='ALL'"
                class="ms-2 mb-2 text-medium"
                :prepend-icon="task.stage === 'IN_PROGRESS' ? 'mdi-progress-check':
             task.stage === 'TO_DO' ? 'mdi-progress-helper' : 'mdi-checkbox-check-all' "
                color="#1e90ff"
                size="small"
                :text="task.stage === 'IN_PROGRESS' ? ' In Progress (' + task.progress+ '% DONE)':
             task.stage === 'TO_DO' ? 'To Do' : 'Completed'"
                variant="outlined"
            ></v-chip>
          </v-card-item>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import {format, parseISO} from "date-fns";

export default {
  data() {
    return {
      tasks: this.$store?.state?.tasks ?? [],
      stages: ['TO_DO', 'IN_PROGRESS', 'COMPLETED'],
      currentStage: 'TO_DO',
      searchBar: '',
    };
  },
  methods: {
    parseISO,
    format,
    addTask() {
      this.$store?.commit('setShowAddTask', true);
      this.$store?.commit('setShowTasks', false);
    },
    openTask(task) {
      this.$store?.commit('setTaskPage', task);
      this.$store?.commit('setShowTasks', false);
      this.$store?.commit('fetchTaskDependenciesByDependsOnId', task.id);
      this.$store?.commit('fetchTaskDependenciesByTaskId', task.id);
    },
  },
};
</script>

<style>
.zone {
  width: 85%;
  height: 94%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
}

.tasks {
  width: 100%;
  height: 96%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(660px, 1fr)); /* Adjust the minimum and maximum width of each column */
  gap: -25px; /* Adjust the gap between cards */
  overflow: auto; /* Add scrollbar when needed */
}

.task {
  width: 600px; /* Adjust the width of your card */
  height: 400px; /* Adjust the height of your card */
  margin-top: 100px;
  margin-right: 150px; /* Adjust the margin between cards */
  margin-left: 120px; /* Adjust the margin between cards */
  border: 1px solid #ccc;
  border-radius: 25px;
  box-shadow: -17px 17px 7px 0px rgba(0, 0, 0, 0.13), 0px 1px 2px 0px rgba(0, 0, 0, 0.11);
  padding: 20px;
  background-color: white;
  display: flex;
  align-content: stretch;
  flex-direction: column;
  justify-content: space-between;
}


.menu_button2 {
  background-color: white;
  padding: 10px 50px;
  font-weight: bold;
  border-radius: 35px;
  justify-content: center;
  font-size: x-large;
  color: black;
  margin-up: 10%;
  margin-bottom: 50%;
}
</style>