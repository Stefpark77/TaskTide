<template>
  <div v-if="this.$store?.state?.showTasks">
    <div class="zone">
      <div class="flex justify-end">
        <button
            class="mt-5 mr-10 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl border-gray-50 border-2 focus:outline-none focus:shadow-outline"
            type="button"
            v-on:click="smartSort">
          Smart Sort
        </button>
        <button
            class="mt-5 mr-10 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl border-gray-50 border-2 focus:outline-none focus:shadow-outline"
            type="button"
            v-on:click="addTask">
          Add New Task?
        </button>
      </div>
      <div class="tasks">
        <div class="task" v-for="task in this.$store?.state?.tasks" :key="task.id">
          <div class="name_and_button">
            <a class="font-bold ">{{ task.name }}</a>
            <button
                class="bg-white hover:bg-blue-700 text-black  hover:text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="button" @click="openTask(task)">
              ...
            </button>
          </div>
          <div class="task_properties">
            <a><b>STAGE:</b> {{ task.stage }}</a>
            <a><b>PRIORITY:</b> {{ task.priority }}</a>
            <a><b>DIFFICULTY:</b> {{ task.difficulty }}</a>
            <a><b>PROGRESS:</b> {{ task.progress }}%</a>
          </div>
          <div class="description_container3">
            <div class="description">
              {{task.description.length > 255 ? task.description.slice(0, 255) + '...' : task.description }}
            </div>
          </div>

          <div class="task_properties">
            <a><b>PROJECT:</b> {{ task.projectId == null ? 'unassigned' : task.projectId}}</a>
          </div>
          <div class="deadlines" v-if="task.projectId !== null">
            <a class="deadline_text"><a class="text-red-500">DEADLINE:</a> {{ format(parseISO(task.deadline),"MMMM dd, yyyy")}}</a>
          </div>
        </div>
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

    };
  },
  methods: {
    parseISO,
    format,
    smartSort() {
      this.$store?.commit('fetchTasks', true);
      this.tasks = this.$store?.state?.tasks;
    },
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
    updateTask(task) {
      this.$store?.commit('setUpdateTask', task);
      this.$store?.commit('setShowTasks', false);
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
.task_properties {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}
.deadlines{
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}
.deadline_text{
  font-size: large;
  font-weight: bold;
}
.name_and_button{
  display: flex;
  justify-content: space-between;
  margin-bottom: 1%;
  font-size: x-large;
}
.description_container3{
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 1%;
  min-height: 250px;
}
.description{
  font-size: large;
  display: flex;
  justify-content: space-evenly;
  border: 1px solid #ccc;
  border-radius: 25px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 85%;
  height: 94%;
  padding: 20px;

  margin-bottom: 1%;
}

.tasks {
  width: 100%;
  height: 90%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(600px, 1fr)); /* Adjust the minimum and maximum width of each column */
  gap: 50px; /* Adjust the gap between cards */
  overflow: auto; /* Add scrollbar when needed */
}
.task {
  width: 500px; /* Adjust the width of your card */
  min-height: 500px; /* Adjust the height of your card */
  margin-right: 150px; /* Adjust the margin between cards */
  margin-left: 150px; /* Adjust the margin between cards */
  border: 1px solid #ccc;
  border-radius: 25px;
  box-shadow: 0px 8px 131px -20px rgba(0,0,0,0.45);
  padding: 20px;
  background-color: white;
  display: flex;
  align-content: stretch;
  flex-direction: column;
  justify-content: space-between;
}
</style>