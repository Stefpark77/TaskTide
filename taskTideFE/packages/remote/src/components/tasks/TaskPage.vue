<template>
  <div v-if="this.$store?.state?.showTaskPage">
    <div class="taskPage">
      <div class="taskPage1st">
        <div class="name_and_button">
          <a class="font-bold ">{{ task.name }}</a>
          <a class="font-bold ">PRIORITY {{ task.priority }}</a>
        </div>
        <div class="task_properties2">
          <a class="border-t-2"><b>DIFFICULTY:</b> {{ task.difficulty }}</a>
          <a>{{ task.stage }} {{ task.stage === 'IN_PROGRESS' ? '(' + task.progress + ' % done)' : '' }}</a>
        </div>
        <div class="description_container2">
          <div class="description2">
            {{ task.description }}
          </div>
        </div>
        <a><b>PROJECT:</b> {{ task.projectId === undefined || task.projectId == null ? 'unassigned' : task.projectId }}
          <a class="deadline_text" v-if="!task.projectId === undefined && !task.projectId == null"><a
              class="text-red-500">DEADLINE:</a> {{ format(parseISO(task.deadline), "MMMM dd, yyyy") }}</a>
        </a>
        <div class="task_properties">
          <a class="font-bold " v-if="taskDependencies !== []">Depends on:</a>
          <table class="table-auto w-full content-center">
            <tbody class="text-center">
            <tr v-for="taskDependency in this.$store?.state?.taskDependencies" :key="taskDependency.id">
              <td class="border px-4 py-2">{{ taskDependency.name }}</td>
              <td class="border px-4 py-2">STAGE: {{ taskDependency.stage }}</td>
              <td class="border px-4 py-2">PRIORITY: {{ taskDependency.priority }}</td>
              <td class="border px-4 py-2">
                <button
                    class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                    type="button" @click="removeTaskDependency(task.id, taskDependency.id)">
                  Delete
                </button>
              </td>
              <td class="border px-4 py-2">
                <button
                    class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                    type="button" @click="openTask(taskDependency)">
                  ...
                </button>
              </td>
            </tr>
            <tr>
              <td class="border px-4 py-2">
                <select
                    class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                    id="description"  v-model="addPriority">
<!--                  <option v-for="otherTask in this.$store?.state?.tasks"
                          :key="otherTask.id"
                          v-if="!taskDependenciesIds.includes(otherTask) && !taskDependenciesOnIds.includes(otherTask) && otherTask.id !== task.id">
                    {{ otherTask.name + ' ' + '(Stage: ' + otherTask.stage + ' | Priority: ' + otherTask.priority + ')'}}
                  </option>-->
                </select>
              </td>
              <td class="border px-4 py-2">
                <button
                    class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                    type="button" @click="">
                  Add New Dependency?
                </button>
              </td>
            </tr>
            </tbody>
          </table>

          <a class="font-bold " v-if="taskDependenciesOn !== []">Is depended by:</a>
          <table class="table-auto w-full content-center">
            <tbody class="text-center">
            <tr v-for="taskDependency in this.$store?.state?.taskDependenciesOn" :key="taskDependency.id">
              <td class="border px-4 py-2">{{taskDependency.name }}</td>
              <td class="border px-4 py-2">STAGE: {{ taskDependency.stage }}</td>
              <td class="border px-4 py-2">PRIORITY: {{ taskDependency.priority }}</td>
              <td class="border px-4 py-2">
                <button
                  class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" @click="removeTaskDependency(taskDependency.id, task.id)">
                Delete
              </button>
              </td>
              <td class="border px-4 py-2">
                <button
                    class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                    type="button" @click="openTask(taskDependency)">
                  ...
                </button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="flex items-end justify-end pt-3.5">
          <button
              class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="removeTask(task.id)">
            Delete
          </button>
          <button
              class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="updateTask(task)">
            Update
          </button>
        </div>
      </div>
      <div class="taskPage2nd">
        <button
            class="h-10 left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-3xl focus:outline-none focus:shadow-outline"
            type="button" @click="exitTask">
          X
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import {format, parseISO} from "date-fns";
export default {
  data() {
    return {
      task: this.$store?.state?.task ?? '',
      token: this.$store?.state?.token,
      tasks: this.$store?.state?.tasks ?? [],
      taskDependencies: this.$store?.state?.taskDependencies ?? [],
      taskDependenciesOn: this.$store?.state?.taskDependenciesOn ?? [],
      /*taskDependenciesIds: this.$store?.state?.taskDependencies.map(task => task.id),
      taskDependenciesOnIds: this.$store?.state?.taskDependenciesOn.map(task => task.id)*/
    };
  },
  watch: {},
  methods: {
    parseISO,
    format,
    updateTask(task) {
      this.$store?.commit('setUpdateTask', task);
      this.$store?.commit('setShowTaskPage', false);
    },
    exitTask() {
      this.$store?.commit('setShowTaskPage', false);
      this.$store?.commit('setShowTasks', true);
    },
    removeTask(id) {
      this.$store?.commit('removeTask', id);
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTasks', true);
    },
    removeTaskDependency(id, dependsOnId) {
      this.$store?.commit('removeTaskDependency', id, dependsOnId);
      this.$store?.commit('fetchTaskDependenciesByTaskId', false);
      this.$store?.commit('fetchTaskDependenciesByDependsOnId', false);
    },
    openTask(task) {
      this.$store?.commit('setTaskPage', task);
      this.$store?.commit('fetchTaskDependenciesByDependsOnId', task.id);
      this.$store?.commit('fetchTaskDependenciesByTaskId', task.id);
      this.task= this.$store?.state?.task ?? '';
      this.taskDependencies = this.$store?.state?.taskDependencies ?? [];
      this.taskDependenciesOn = this.$store?.state?.taskDependenciesOn ?? [];
     /* this.taskDependenciesIds = this.taskDependencies.map(task => task.id);
      this.taskDependenciesOnIds = this.taskDependenciesOn.map(task => task.id);*/
    },
  },
};
</script>


<style>

.taskPage {
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

.taskPage1st {
  background-color: white;
  width: 70%;
  height: 70%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Adjust this value to match Tailwind's shadow-md */
  border-radius: 1.5rem; /* equivalent to rounded-3xl */
  padding-left: 2rem; /* equivalent to px-8 */
  padding-right: 2rem; /* equivalent to px-8 */
  padding-top: 1.5rem; /* equivalent to pt-6 */
  padding-bottom: 2rem; /* equivalent to pb-8 */
  margin-bottom: 1rem; /* equivalent to mb-4 */
}

.taskPage2nd {
  display: flex;
  flex-direction: row;
  width: auto;
  height: 90%;
  padding: 3%;
}

.task_properties2 {
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
  min-height: 60%;
  padding: 10px;
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
}

</style>