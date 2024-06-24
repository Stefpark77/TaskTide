<template>
  <div class="menu">
    <div class="account">
      <div class="account_info">
        <div class="account_image">
          <img class="account_image2"
               src="https://t4.ftcdn.net/jpg/00/64/67/27/360_F_64672736_U5kpdGs9keUll8CRQ3p3YaEv2M6qkVY5.jpg">
        </div>
      </div>
      <div class="account_info">
        <a>
          <v-icon icon="mdi-account"/>
          {{ this.$store?.state?.currentUser.username }}</a>
        <a>
          <v-icon icon="mdi-email-outline"/>
          {{ this.$store?.state?.currentUser.email }}</a>
      </div>
    </div>
    <div class="menu_buttons">
      <v-tabs
          color="#1e90ff"
          :model-value="this.$store?.state?.showEvents || this.$store?.state?.showEditEvent ||this.$store?.state?.showAddEvent ? 'Agenda' :
           this.$store?.state?.showProjects || this.$store?.state?.showProjectPage || this.$store?.state?.showEditProject ||this.$store?.state?.showAddProject ? 'Projects' :
           'Tasks'"
          direction="vertical">
        <v-tab class="menu_button" prepend-icon="mdi-calendar-clock" text="Agenda" value="Agenda"
               @click="agenda"></v-tab>
        <v-tab class="menu_button" prepend-icon="mdi-file-tree" text="Projects" value="Projects"
               @click="projectsPage"></v-tab>
        <v-tab class="menu_button" prepend-icon="mdi-bulletin-board" text="Tasks" value="Tasks"
               @click="tasksPage"></v-tab>

      </v-tabs>
    </div>
    <div class="logout_button">
      <v-btn
          rounded="xl"
          size="x-large"
          color="#1e90ff"
          class="standard_button"
          variant="flat" @click="logout">
        SIGN OUT
      </v-btn>
    </div>
  </div>
  <div class="title">
    <h1 class="title_text">TASKTIDE
      <v-icon icon="mdi-checkbox-marked-circle-outline"/>
    </h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: this.$store?.state?.username ?? "Unknown Username",
      email: this.$store?.state?.email ?? "Unknown Email",
      events: this.$store?.state?.events ?? [],
      projects: this.$store?.state?.projects ?? [],
      tasks: this.$store?.state?.tasks ?? [],
    };
  },
  methods: {

    agenda() {
      this.$store?.commit('setShowEvents', true);
      this.$store?.commit('setShowTasks', false);
      this.$store?.commit('setShowProjects', false);
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEditEvent', false);
      this.$store?.commit('setShowAddTask', false);
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTaskPage', false);
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowEditProject', false);
      this.$store?.commit('setShowProjectPage', false);
    },
    tasksPage() {
      this.$store?.commit('setShowEvents', false);
      this.$store?.commit('setShowTasks', false);
      this.$store?.commit('fetchTasks', true);
      this.$store?.commit('setShowProjects', false);
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEditEvent', false);
      this.$store?.commit('setShowAddTask', false);
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTaskPage', false);
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowEditProject', false);
      this.$store?.commit('setShowProjectPage', false);
    },
    projectsPage() {
      this.$store?.commit('setShowEvents', false);
      this.$store?.commit('setShowTasks', false);
      this.$store?.commit('setShowProjects', true);
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEditEvent', false);
      this.$store?.commit('setShowAddTask', false);
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTaskPage', false);
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowEditProject', false);
      this.$store?.commit('setShowProjectPage', false);
    },
    logout() {
      this.$store?.commit('logout');
    },
  },
};
</script>

<style>
.menu {
  width: 15%;
  height: 100%;
  left: 0;
  bottom: 0;
  position: absolute;
  background-repeat: repeat;
  background-color: white;
  display: flex;
  align-content: stretch;
  flex-direction: column;
  justify-content: space-between;
  box-shadow: 17px -2px 12px -5px rgba(32, 29, 29, 0.13),
  0px 1px 1px 0px rgba(0, 0, 0, 0.11);
  z-index: 200;
}

.title {
  width: 85%;
  height: 6%;
  right: 0;
  top: 0;
  position: absolute;
  background-color: white;
  font-size: xx-large;
  font-weight: bold;
  align-items: center;
  display: flex;
  justify-content: center;
  box-shadow: 14px 17px 12px -5px rgba(32, 29, 29, 0.13), 0px 1px 1px 0px rgba(0, 0, 0, 0.11);
  z-index: 201;
}

.title_text {
  margin-right: 75px;
}

.account {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  flex-direction: column;
  align-content: space-around;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.account_image {
  display: flex;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  position: relative;
  overflow: hidden;
  justify-content: center;
  flex-wrap: wrap;
  flex-direction: column;
  margin-bottom: 10px;
}

.account_image2 {
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
}

.account_info {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  flex-direction: column;
  font-size: x-large;
  font-weight: bold;
  align-items: center;
}

.menu_buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  flex-direction: column;
  font-size: x-large;
  font-weight: bold;
  align-items: center;
}

.menu_button:hover {
  background-color: dodgerblue;
  color: white;
  padding: 10px 50px;
  font-weight: bold;
  border-radius: 35px;
  justify-content: center;
  font-size: x-large;
}

.menu_button {
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

.logout_button {
  bottom: 0;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-bottom: 30px;
}

.standard_button {
  background-color: dodgerblue;
  color: white;
  padding: 10px 50px;
  font-weight: bold;
  border-radius: 35px;
  justify-content: center;
  font-size: x-large;
}

.standard_button:hover {
  background-color: royalblue;
  outline: none;
  box-shadow: none;
  font-family: Arial, Helvetica, sans-serif
}
</style>