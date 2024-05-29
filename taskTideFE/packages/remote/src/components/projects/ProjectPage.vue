<template>
  <div v-if="this.$store?.state?.showProjectPage">
    <div class="projectPage">
      <div class="projectPage1st">
        <div class="name_and_button">
          <a class="font-bold ">{{ project.name }}</a>
        </div>

        <div class="task_properties2">
          <div class="border-t-2">
          <a class="deadline_text"><a class="text-red-500">DEADLINE:</a> {{ format(parseISO(project.deadline),"MMMM dd, yyyy")}}</a>
          </div>
        </div>
        <div class="description_container2">
          <div class="description2">
            {{ project.description }}
          </div>
        </div>
        <div class="project_properties">
          <a class="font-bold ">Tasks:</a>
          <div class="table_container">
          <table class="table-auto w-full content-center">
            <tbody class="text-center">
            <tr v-for="projectTask in this.$store?.state?.projectTasks" :key="projectTask.id">
              <td class="border px-4 py-2">{{ projectTask.name }}</td>
              <td class="border px-4 py-2">STAGE: {{ projectTask.stage }}</td>
              <td class="border px-4 py-2">PRIORITY: {{ projectTask.priority }}</td>
              <td class="border px-4 py-2">
                <button
                    class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                    type="button" @click="removeTaskForProject(projectTask)">
                  Remove from Project
                </button>
              </td>
              <td class="border px-4 py-2">
                <button
                    class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                    type="button" @click="openTask(projectTask)">
                  ...
                </button>
              </td>
            </tr>
            <tr>
              <td class="border px-4 py-2">
                <select
                    class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                    id="description"  v-model="toAddTask">
                  <option v-for="task in addableTasks"
                          :key="task.id"
                          :value="task">
                    {{ task.name + ' ' + '(Stage: ' + task.stage + ' | Priority: ' + task.priority + ')'}}
                  </option>
                </select>
              </td>
              <td class="border px-4 py-2"
                  v-if="toAddTask !== undefined && toAddTask !== '' && toAddTask !== null">
                <button
                    class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                    type="button" @click="addTaskForProject(toAddTask)">
                  Add New Task?
                </button>
              </td>
            </tr>
            </tbody>
          </table>
          </div>
          <a class="font-bold ">Users:</a>
          <table class="table-auto w-full content-center">
            <tbody class="text-center">
            <tr v-for="user in this.$store?.state?.projectUsers" :key="user.id">
              <td class="border px-4 py-2">{{user.username }}</td>
              <td class="border px-4 py-2">Email: {{ user.email }}</td>
              <td class="border px-4 py-2">Full Name: {{ user.firstName + ' '+ user.lastName }}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="flex items-end justify-end pt-3.5">
          <button v-if="!this.$store?.state?.projectUsers.map(pUser => pUser.id).includes(this.$store?.state?.currentUser.id)"
              class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="enrollToProject">
            Enroll to Project
          </button>
          <button v-if="this.$store?.state?.projectUsers.map(pUser => pUser.id).includes(this.$store?.state?.currentUser.id)"
                  class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" @click="exitFromProject">
            Exit Project
          </button>
          <button
              class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="removeProject(project.id)">
            Delete
          </button>
          <button
              class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="updateProject(project)">
            Update
          </button>
        </div>
      </div>
      <div class="projectPage2nd">
        <button
            class="h-10 left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-3xl focus:outline-none focus:shadow-outline"
            type="button" @click="exitProject">
          X
        </button>
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
    removeProject(id) {
      this.$store?.commit('removeProject', id);
      this.$store?.commit('setShowProjectPage', false);
      this.$store?.commit('setShowProjects', true);
    },
    addTaskForProject(task) {
      this.$store?.commit('updateTaskForProject', {task: task, project: this.project});
      this.$store?.commit('setShowProjects', true);
      this.$store?.commit('fetchProjects', true);
    },
    removeTaskForProject(task) {
      this.$store?.commit('updateTaskForProject', {task: task, project: {id:null, deadline:null}});
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

</style>