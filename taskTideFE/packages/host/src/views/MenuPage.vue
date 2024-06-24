<template>
  <Menu/>
  <Events/>
  <div class="mt-10"/>
  <div v-if="showAddEvent">
    <AddEvent/>
  </div>
  <div v-if="showEditEvent">
    <UpdateEvent/>
  </div>
  <div v-if="showAddTask">
    <AddTask/>
  </div>
  <div v-if="showEditTask">
    <UpdateTask/>
  </div>
  <div v-if="showAddProject">
    <AddProject/>
  </div>
  <div v-if="showEditProject">
    <UpdateProject/>
  </div>
  <div v-if="showProjects">
    <Projects/>
  </div>
  <div v-if="showTasks">
    <Tasks/>
  </div>
  <div v-if="showTaskPage">
    <TaskPage/>
  </div>
  <div v-if="showProjectPage">
    <ProjectPage/>
  </div>
  <router-view></router-view>
</template>

<script>
import {defineAsyncComponent} from 'vue';

export default {
  beforeCreate() {
    this.$store?.commit('prepareMenu', true);
    this.$store?.commit('fetchWeekEvents', null);
    this.$store?.commit('fetchTasks', null);
    this.$store?.commit('fetchProjects', true);
    this.$store?.commit('setShowEvents', true);
  },
  mounted() {
    this.$store?.commit('prepareMenu', true);
    this.$store?.commit('fetchWeekEvents', null);
    this.$store?.commit('fetchTasks', null);
    this.$store?.commit('fetchProjects', true);
    this.$store?.commit('setShowEvents', true);
  },
  components: {
    Menu: defineAsyncComponent(() => import('remote/Menu')),
    Events: defineAsyncComponent(() => import('remote/Events')),
    AddEvent: defineAsyncComponent(() => import('remote/AddEvent')),
    UpdateEvent: defineAsyncComponent(() => import('remote/UpdateEvent')),
    Tasks: defineAsyncComponent(() => import('remote/Tasks')),
    AddTask: defineAsyncComponent(() => import('remote/AddTask')),
    UpdateTask: defineAsyncComponent(() => import('remote/UpdateTask')),
    TaskPage: defineAsyncComponent(() => import('remote/TaskPage')),
    Projects: defineAsyncComponent(() => import('remote/Projects')),
    AddProject: defineAsyncComponent(() => import('remote/AddProject')),
    UpdateProject: defineAsyncComponent(() => import('remote/UpdateProject')),
    ProjectPage: defineAsyncComponent(() => import('remote/ProjectPage')),
  },
  computed: {
    showAddEvent() {
      return this.$store?.state?.showAddEvent ?? false;
    },
    showEditEvent() {
      return this.$store?.state?.showEditEvent ?? false;
    },
    showAddTask() {
      return this.$store?.state?.showAddTask ?? false;
    },
    showEditTask() {
      return this.$store?.state?.showEditTask ?? false;
    },
    showAddProject() {
      return this.$store?.state?.showAddProject ?? false;
    },
    showEditProject() {
      return this.$store?.state?.showEditProject ?? false;
    },
    showProjects() {
      return this.$store?.state?.showProjects ?? false;
    },
    showTasks() {
      return this.$store?.state?.showTasks ?? false;
    },
    showTaskPage() {
      return this.$store?.state?.showTaskPage ?? false;
    },
    showProjectPage() {
      return this.$store?.state?.showProjectPage ?? false;
    },
  },
};
</script>