<template>
  <div v-if="this.$store?.state?.showProjectPage">
    <div class="projectPage">
      <v-card class="projectPage1st beautifulShadow">
        <v-card-title class="text-body-2 d-flex align-center border-b-2 mb-5 ">

        <div class="text-h4"><a class="font-weight-bold">
          <v-icon
              color="#1e90ff"
              class="mb-1"
              icon="mdi-text-box-multiple-outline"
              start
          ></v-icon>
          {{ project.name }}</a></div>
        <v-spacer></v-spacer>
        <a class="text-h5"><b class="text-red-500">DEADLINE: </b>{{ format(parseISO(project.deadline), "MMMM dd, yyyy") }}</a>
      </v-card-title>
        <v-textarea class="mt-5" id="description" rows="10" min-width="500px" type="text" label="Description"
                    v-model="project.description" :readonly="true"/>
        <v-card-title class="d-flex align-center pe-2">
          <v-icon icon="mdi-checkbox-blank-outline"></v-icon> &nbsp;
          Tasks:
        </v-card-title>
        <v-divider></v-divider>
        <v-expansion-panels>
          <v-expansion-panel
              v-for="projectTask in this.$store?.state?.projectTasks"
              :key="projectTask.id"
              :value="projectTask"
          >
            <v-expansion-panel-title v-slot="{ expanded }">
              <div>
                <v-icon
                    color="#1e90ff"
                    class="mb-1"
                    icon="mdi-checkbox-blank-outline"
                    start
                ></v-icon>
                {{ projectTask.name }}
              </div>
              <v-spacer></v-spacer>
              <v-chip
                  class="ms-2 text-medium"
                  :prepend-icon="projectTask.stage === 'IN_PROGRESS' ? 'mdi-progress-check':
             projectTask.stage === 'TO_DO' ? 'mdi-progress-helper' : 'mdi-checkbox-check-all' "
                  color="black"
                  size="small"
                  :text="projectTask.stage === 'IN_PROGRESS' ? ' In Progress (' + projectTask.progress+ '% DONE)':
             projectTask.stage === 'TO_DO' ? 'To Do' : 'Completed'"
                  variant="outlined"
              ></v-chip>

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-account"
                  color="#1e90ff"
                  size="small"
                  :text="projectTask.user.firstName+' '+projectTask.user.lastName"
                  v-if="projectTask.userId!==null && projectTask.user!==null && projectTask.user!==undefined"
                  variant="outlined"
              />
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-account"
                  color="grey"
                  size="small"
                  text="No Assigned User"
                  v-if="projectTask.userId===null"
                  variant="outlined"
              />

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-decagram"
                  size="small"
                  color="#1e90ff"
                  :text="'Difficulty: ' + projectTask.difficulty"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="green"
                  size="small"
                  text="Low Priority"
                  v-if="projectTask.priority === 'LOW'"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="orange"
                  size="small"
                  text="Medium Priority"
                  v-if="projectTask.priority === 'MEDIUM'"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="red"
                  size="small"
                  text="High Priority"
                  v-if="projectTask.priority === 'HIGH'"
                  variant="outlined"
              />



            </v-expansion-panel-title>
            <v-expansion-panel-text>
              <v-chip
                  class="bg-red-300 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" prepend-icon="mdi-delete" @click="removeTaskForProject(projectTask)">
                Remove From Project
              </v-chip>
              <v-chip
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" prepend-icon="mdi-eye" @click="openTask(projectTask)">
                Open Task
              </v-chip>
            </v-expansion-panel-text>
          </v-expansion-panel>
        </v-expansion-panels>
        <div class="flex align-center">
          <v-select
              class="mt-5"
              :items="addableTasks"
              :item-title="item => item.name !== undefined ? item.name +' (Stage: '+ item.stage +' | Priority: ' + item.priority +')' : 'Choose a task'"
              :item-value="item => item"
              label="Add New Task?"
              v-model="toAddTask"
          >
          </v-select>
          <v-btn
              icon="mdi-plus"
              class="bg-blue-400 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline ml-2"
              v-if="toAddTask !== ''"
              type="button" @click="addTaskForProject(toAddTask)"/>
        </div>
        <v-card-title class="d-flex align-center pe-2">
          <v-icon icon="mdi-account"></v-icon> &nbsp;
          Users:
        </v-card-title>
        <v-expansion-panels>
          <v-expansion-panel
              v-for="user in this.$store?.state?.projectUsers"
              :key="user.id"
              :value="user"
              :readonly="true"
              :hide-actions="true"
          >
            <v-expansion-panel-title v-slot="{ expanded }">
              <div>
                <v-icon
                    color="#1e90ff"
                    class="mb-1"
                    icon="mdi-account"
                    start
                ></v-icon>
                {{ user.username }}
              </div>
              <v-spacer></v-spacer>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-mail"
                  size="small"
                  color="#1e90ff"
                  :text="user.email"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-account"
                  color="green"
                  size="small"
                  :text="user.firstName+' '+user.lastName"
                  variant="outlined"
              ></v-chip>
            </v-expansion-panel-title>
          </v-expansion-panel>
        </v-expansion-panels>
        <div class="flex items-end justify-end pt-3.5">
          <v-btn v-if="!this.$store?.state?.projectUsers.map(pUser => pUser.id).includes(this.$store?.state?.currentUser.id)"
              class="mr-2 left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="enrollToProject">
            Enroll to Project
          </v-btn>
          <v-btn v-if="this.$store?.state?.projectUsers.map(pUser => pUser.id).includes(this.$store?.state?.currentUser.id)"
                  class="mr-2 bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" @click="exitFromProject">
            Exit Project
          </v-btn>
          <v-btn
              class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="updateProject(project)">
            Update
          </v-btn>
        </div>
      </v-card>
      <div class="projectPage2nd">
        <v-btn
            icon="mdi-close"
            class="h-10 left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-3xl focus:outline-none focus:shadow-outline"
            type="button" @click="exitProject"/>
      </div>
    </div>
  </div>
</template>
<script>
import {format, parseISO} from "date-fns";
export default {
  computed:{
    addableTasks: function () {
      return this.$store?.state?.allTasks.filter(task => !this.$store?.state?.projectTasks.map(pTask => pTask.id).includes(task.id)
          && task.projectId === null )
    },
  },
  data() {
    return {
      project: this.$store?.state?.project ?? '',
      token: this.$store?.state?.token,
      allTasks: this.$store?.state?.allTasks ?? [],
      projectTasks: this.$store?.state?.projectTasks ?? [],
      projectUsers: this.$store?.state?.projectUsers ?? [],
      toAddTask: '',
    };
  },
  watch: {},
  methods: {
    parseISO,
    format,
    updateProject(project) {
      this.$store?.commit('setUpdateProject', project);
      this.$store?.commit('setShowProjectPage', false);
    },
    exitProject() {
      this.$store?.commit('setShowProjectPage', false);
      this.$store?.commit('setShowProjects', true);
      this.$store?.commit('fetchProjects', true);
    },
    addTaskForProject(task) {
      this.$store?.commit('updateTaskForProject', {task: task, project: this.project, projectId: this.project.id});
      this.toAddTask=null;
      this.$store?.commit('setShowProjects', true);
      this.$store?.commit('fetchProjects', true);
    },
    removeTaskForProject(task) {
      this.$store?.commit('updateTaskForProject', {task: task, project: {id:null, deadline:null}, projectId: this.project.id});
      this.toAddTask=null;
      this.$store?.commit('setShowProjects', true);
      this.$store?.commit('fetchProjects', true);
    },
    enrollToProject() {
      this.$store?.commit('createProjectUser', {projectId: this.project.id});
    },
    exitFromProject() {
      this.$store?.commit('removeProjectUser', {projectId: this.project.id});
    },
    openTask(task) {
      task.project = this.project;
      this.$store?.commit('setTaskPage', task);
      this.$store?.commit('setShowProjectPage', false);
      this.$store?.commit('fetchTaskDependenciesByDependsOnId', task.id);
      this.$store?.commit('fetchTaskDependenciesByTaskId', task.id);
    },
  },
};
</script>


<style>

.projectPage {
  width: 85%;
  height: 94%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  display: flex;
  justify-content: center;
  align-items: center;
  color: black;
  flex-direction: row;
}

.projectPage1st {
  background-color: white;
  width: 70%;
  min-height: 70%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Adjust this value to match Tailwind's shadow-md */
  border-radius: 1.5rem; /* equivalent to rounded-3xl */
  padding-left: 2rem; /* equivalent to px-8 */
  padding-right: 2rem; /* equivalent to px-8 */
  padding-top: 1.5rem; /* equivalent to pt-6 */
  padding-bottom: 2rem; /* equivalent to pb-8 */
  margin-bottom: 1rem; /* equivalent to mb-4 */
  max-height: 95%;
  overflow: auto;
}

.projectPage2nd {
  display: flex;
  flex-direction: row;
  width: auto;
  height: 90%;
  padding: 3%;
}

.project_properties2 {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  border-top: gray 2px;
}

.description_container2 {
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 1%;
  min-width: 90%;
  min-height: 50%;
  padding: 10px;
  overflow: auto;
}
.table_container {
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 1%;
  min-width: 90%;
  padding: 10px;
  max-height: 100px;
  overflow: auto;
}

.description2 {
  font-size: large;
  display: flex;
  justify-content: flex-start;
  border: 1px solid #ccc;
  border-radius: 25px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 100%;
  padding: 20px;
  height: auto;
  margin-bottom: 1%;
  overflow: auto; /* Add scrollbar when needed */
}

.beautifulShadow {
  box-shadow: -17px 17px 7px 0px rgba(0, 0, 0, 0.13), 0px 1px 2px 0px rgba(0, 0, 0, 0.11);
}
</style>