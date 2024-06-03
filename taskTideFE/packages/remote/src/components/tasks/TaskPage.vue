<template>
  <div v-if="this.$store?.state?.showTaskPage">
    <div class="taskPage">
      <v-card class="taskPage1st beautifulShadow">

        <v-card-title class="text-body-2 d-flex align-center border-b-2 mb-5 ">

          <div class="text-h4"><a class="font-weight-bold">
            <v-icon
                color="#1e90ff"
                class="mb-1"
                icon="mdi-checkbox-blank-outline"
                start
            ></v-icon>
            {{ task.name }}</a></div>
          <v-spacer></v-spacer>
          <v-chip
              class="ms-2 text-medium"
              :prepend-icon="task.stage === 'IN_PROGRESS' ? 'mdi-progress-check':
             task.stage === 'TO_DO' ? 'mdi-progress-helper' : 'mdi-checkbox-check-all' "
              color="black"
              size="large"
              :text="task.stage === 'IN_PROGRESS' ? ' In Progress (' + task.progress+ '% DONE)':
             task.stage === 'TO_DO' ? 'To Do' : 'Completed'"
              variant="outlined"
          ></v-chip>
          <v-chip
              class="ms-2 text-medium"
              prepend-icon="mdi-decagram"
              size="large"
              color="#1e90ff"
              :text="'Difficulty: ' + task.difficulty"
              variant="outlined"
          ></v-chip>
          <v-chip
              class="ms-2 text-medium"
              prepend-icon="mdi-checkbox-blank-circle"
              color="green"
              size="large"
              text="Low Priority"
              v-if="task.priority === 'LOW'"
              variant="outlined"
          ></v-chip>
          <v-chip
              class="ms-2 text-medium"
              prepend-icon="mdi-checkbox-blank-circle"
              color="orange"
              size="large"
              text="Medium Priority"
              v-if="task.priority === 'MEDIUM'"
              variant="outlined"
          ></v-chip>
          <v-chip
              class="ms-2 text-medium"
              prepend-icon="mdi-checkbox-blank-circle"
              color="red"
              size="large"
              text="High Priority"
              v-if="task.priority === 'HIGH'"
              variant="outlined"
          ></v-chip>
        </v-card-title>

        <a>
          <v-icon color="#1e90ff" icon="mdi-account"/>
          <b> User:</b> {{ task.userId == null ? 'unassigned' : task.user.firstName + ' ' + task.user.lastName }}
          <a class="switch_button" v-if="task.userId !== currentUser.id" v-on:click="updateUserTask(currentUser.id)">
            ( Assign yourself? )
          </a>
          <a class="switch_button" v-if="task.userId === currentUser.id" v-on:click="updateUserTask(null)">
            ( Unassign? )
          </a>
        </a>
        <v-divider></v-divider>
        <a>
          <v-icon color="#1e90ff" icon="mdi-text-box-multiple-outline"/>
          <b> PROJECT:</b> {{ task.projectId == null ? 'unassigned' : task.project.name }}</a>
        <v-divider></v-divider>
        <a v-if="task.projectId !== null && task.deadline !== null">
          <a class="text-red-500">
            <v-icon icon="mdi-calendar-clock-outline"/>
            <b> DEADLINE:</b></a> {{ format(parseISO(task.deadline), "MMMM dd, yyyy") }} </a>
        <v-textarea class="mt-5" id="description" rows="10" min-width="500px" type="text" label="Description"
                    v-model="task.description" :readonly="true"/>

        <v-card-title class="d-flex align-center pe-2">
          <v-icon icon="mdi-chevron-double-left"></v-icon> &nbsp;
          Depends on:
        </v-card-title>
        <v-divider></v-divider>
        <v-expansion-panels>
          <v-expansion-panel
              v-for="taskDependency in this.$store?.state?.taskDependencies"
              :key="taskDependency.id"
              :value="taskDependency"
          >
            <v-expansion-panel-title v-slot="{ expanded }">
              <div>
                <v-icon
                    color="#1e90ff"
                    class="mb-1"
                    icon="mdi-checkbox-blank-outline"
                    start
                ></v-icon>
                {{ taskDependency.name }}
              </div>
              <v-spacer></v-spacer>
              <v-chip
                  class="ms-2 text-medium"
                  :prepend-icon="taskDependency.stage === 'IN_PROGRESS' ? 'mdi-progress-check':
             taskDependency.stage === 'TO_DO' ? 'mdi-progress-helper' : 'mdi-checkbox-check-all' "
                  color="black"
                  size="small"
                  :text="taskDependency.stage === 'IN_PROGRESS' ? ' In Progress (' + taskDependency.progress+ '% DONE)':
             taskDependency.stage === 'TO_DO' ? 'To Do' : 'Completed'"
                  variant="outlined"
              ></v-chip>

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-account"
                  color="#1e90ff"
                  size="small"
                  :text="taskDependency.user.firstName+' '+taskDependency.user.lastName"
                  v-if="taskDependency.userId!==null && taskDependency.user!==null && taskDependency.user!==undefined"
                  variant="outlined"
              />
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-account"
                  color="grey"
                  size="small"
                  text="No Assigned User"
                  v-if="taskDependency.userId===null"
                  variant="outlined"
              />

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-decagram"
                  size="small"
                  color="#1e90ff"
                  :text="'Difficulty: ' + taskDependency.difficulty"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="green"
                  size="small"
                  text="Low Priority"
                  v-if="taskDependency.priority === 'LOW'"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="orange"
                  size="small"
                  text="Medium Priority"
                  v-if="taskDependency.priority === 'MEDIUM'"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="red"
                  size="small"
                  text="High Priority"
                  v-if="taskDependency.priority === 'HIGH'"
                  variant="outlined"
              ></v-chip>
            </v-expansion-panel-title>
            <v-expansion-panel-text>
              <v-chip
                  class="bg-red-300 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" prepend-icon="mdi-delete" @click="removeTaskDependency(task.id, taskDependency.id)">
                Delete Dependency
              </v-chip>
              <v-chip
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" prepend-icon="mdi-eye" @click="openTask(taskDependency)">
                Open Task
              </v-chip>
            </v-expansion-panel-text>
          </v-expansion-panel>
        </v-expansion-panels>
        <div class="flex align-center">
          <v-select
              class="mt-5"
              :items="addableDependencies"
              :item-title="item => item.name !== undefined ? item.name +' (Stage: '+ item.stage +' | Priority: ' + item.priority +')' : 'Choose a task'"
              item-value="id"
              label="Add New Dependency?"
              v-model="toAddDependency"
          >
          </v-select>
          <v-btn
              icon="mdi-plus"
              class="bg-blue-400 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline ml-2"
              v-if="toAddDependency !== ''"
              type="button" @click="addTaskDependency(task.id, toAddDependency)"/>
        </div>
        <v-card-title class="d-flex align-center pe-2">
          <v-icon icon="mdi-chevron-double-right"></v-icon> &nbsp;
          Is depended by:
        </v-card-title>
        <v-divider></v-divider>
        <v-expansion-panels>
          <v-expansion-panel
              v-for="taskDependency in this.$store?.state?.taskDependenciesOn"
              :key="taskDependency.id"
              :value="taskDependency"
          >
            <v-expansion-panel-title v-slot="{ expanded }">
              <div></div>
              <div>
                <v-icon
                    color="#1e90ff"
                    class="mb-1"
                    icon="mdi-checkbox-blank-outline"
                    start
                ></v-icon>
                {{ taskDependency.name }}
              </div>
              <v-spacer></v-spacer>
              <v-chip
                  class="ms-2 text-medium"
                  :prepend-icon="taskDependency.stage === 'IN_PROGRESS' ? 'mdi-progress-check':
             taskDependency.stage === 'TO_DO' ? 'mdi-progress-helper' : 'mdi-checkbox-check-all' "
                  color="black"
                  size="small"
                  :text="taskDependency.stage === 'IN_PROGRESS' ? ' In Progress (' + taskDependency.progress+ '% DONE)':
             taskDependency.stage === 'TO_DO' ? 'To Do' : 'Completed'"
                  variant="outlined"
              ></v-chip>


              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-account"
                  color="#1e90ff"
                  size="small"
                  :text="taskDependency.user.firstName+' '+taskDependency.user.lastName"
                  v-if="taskDependency.userId!==null && taskDependency.user!==null && taskDependency.user!==undefined"
                  variant="outlined"
              />
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-account"
                  color="grey"
                  size="small"
                  text="No Assigned User"
                  v-if="taskDependency.userId===null"
                  variant="outlined"
              />

              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-decagram"
                  size="small"
                  color="#1e90ff"
                  :text="'Difficulty: ' + taskDependency.difficulty"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="green"
                  size="small"
                  text="Low Priority"
                  v-if="taskDependency.priority === 'LOW'"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="orange"
                  size="small"
                  text="Medium Priority"
                  v-if="taskDependency.priority === 'MEDIUM'"
                  variant="outlined"
              ></v-chip>
              <v-chip
                  class="ms-2 text-medium"
                  prepend-icon="mdi-checkbox-blank-circle"
                  color="red"
                  size="small"
                  text="High Priority"
                  v-if="taskDependency.priority === 'HIGH'"
                  variant="outlined"
              ></v-chip>
            </v-expansion-panel-title>
            <v-expansion-panel-text>
              <v-chip
                  class="bg-red-300 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" prepend-icon="mdi-delete" @click="removeTaskDependency(taskDependency.id, task.id)">
                Delete Dependency
              </v-chip>
              <v-chip
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" prepend-icon="mdi-eye" @click="openTask(taskDependency)">
                Open Task
              </v-chip>
            </v-expansion-panel-text>
          </v-expansion-panel>
        </v-expansion-panels>
        <div class="flex align-center">
          <v-select
              class="mt-5"
              :items="addableDependencies"
              :item-title="item => item.name !== undefined ? item.name +' (Stage: '+ item.stage +' | Priority: ' + item.priority +')' : 'Choose a task'"
              item-value="id"
              label="Add New Dependency?"
              placeholder="Choose Task"
              v-model="toAddDependencyOn"
          >
          </v-select>
          <v-btn
              icon="mdi-plus"
              class="bg-blue-400 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline ml-2"
              v-if="toAddDependencyOn !== ''"
              type="button" @click="addTaskDependency(toAddDependencyOn, task.id)"/>
        </div>

        <div class="flex items-end justify-end pt-3.5">
          <v-btn
              class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
              type="button" @click="updateTask(task)">
            Update
          </v-btn>
        </div>
      </v-card>
      <div class="taskPage2nd">
        <v-btn
            icon="mdi-close"
            class="h-10 left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-3xl focus:outline-none focus:shadow-outline"
            type="button" @click="exitTask"/>
      </div>
    </div>
  </div>
</template>
<script>
import {format, parseISO} from "date-fns";

export default {
  computed: {
    addableDependencies: function () {
      return this.$store?.state?.tasks.filter(otherTask => !this.$store?.state?.taskDependencies.map(task => task.id).includes(otherTask.id)
          && !this.$store?.state?.taskDependenciesOn.map(task => task.id).includes(otherTask.id)
          && otherTask.id !== this.task.id)
    },
  },
  data() {
    return {
      task: this.$store?.state?.task ?? '',
      currentUser: this.$store?.state?.currentUser ?? '',
      token: this.$store?.state?.token,
      tasks: this.$store?.state?.tasks ?? [],
      taskDependencies: this.$store?.state?.taskDependencies ?? [],
      taskDependenciesOn: this.$store?.state?.taskDependenciesOn ?? [],
      toAddDependency: '',
      toAddDependencyOn: '',
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
      /*this.$store?.commit('fetchTasks', true);*/
    },
    removeTask(id) {
      this.$store?.commit('removeTask', id);
      this.$store?.commit('setShowTaskPage', false);
      this.$store?.commit('setShowTasks', true);
    },
    removeTaskDependency(id, dependsOnId) {
      this.$store?.commit('removeTaskDependency', {taskId: id, dependsOnId: dependsOnId});
    },
    addTaskDependency(id, dependsOnId) {
      this.$store?.commit('createTaskDependency', {taskId: id, dependsOnId: dependsOnId});
      this.$store?.commit('fetchTaskDependenciesByDependsOnId', this.task.id);
      this.$store?.commit('fetchTaskDependenciesByTaskId', this.task.id);
    },
    openTask(task) {
      this.$store?.commit('setTaskPage', task);
      this.$store?.commit('fetchTaskDependenciesByDependsOnId', task.id);
      this.$store?.commit('fetchTaskDependenciesByTaskId', task.id);
      this.task = this.$store?.state?.task ?? '';
      this.taskDependencies = this.$store?.state?.taskDependencies ?? [];
      this.taskDependenciesOn = this.$store?.state?.taskDependenciesOn ?? [];
    },
    updateUserTask(userId) {
      this.$store?.commit('updateTask', {
        name: this.task.name,
        description: this.task.description,
        difficulty: this.task.difficulty,
        priority: this.task.priority,
        stage: this.task.stage,
        progress: this.task.progress,
        userId: userId,
        projectId: this.task.projectId,
        deadline: this.task.deadline,
      });
      if (userId == null) {
        this.task.userId = null;
      } else {
        this.task.user = this.currentUser;
        this.task.userId = this.currentUser.id;
      }

    }
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
  min-height: 70%;
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
  min-height: 50%;
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

.switch_button {
  color: dodgerblue;
  font-weight: bold;
  font-style: italic;
}

.switch_button:hover {
  color: royalblue;
  font-style: normal;
  cursor: pointer;
}

.beautifulShadow {
  box-shadow: -17px 17px 7px 0px rgba(0, 0, 0, 0.13), 0px 1px 2px 0px rgba(0, 0, 0, 0.11);
}
</style>