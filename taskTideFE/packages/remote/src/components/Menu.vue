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
        <a>{{username}}</a>
        <a>{{email}}</a>
      </div>
    </div>
    <div class="menu_buttons">
      <div>
        <button
            class="menu_button"
            type="button" @click="agenda">
          O   Agenda
        </button>
      </div>
      <div>
        <button
            class="menu_button"
            type="button" @click="tasksPage">
          O   Tasks
        </button>
      </div>
      <div>
        <button
            class="menu_button"
            type="button" @click="projectsPage">
          O   Projects
        </button>
      </div>
    </div>
    <div class="logout_button">
      <button
          class="standard_button"
          type="button" @click="logout">
        Log Out
      </button>
    </div>
  </div>
  <div class="title">
    <h1 class="title_text">TaskTide</h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username:this.$store?.state?.username ?? "Unknown Username",
      email:this.$store?.state?.email ?? "Unknown Email",
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
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowEditProject', false);
    },
    tasksPage() {
      this.$store?.commit('setShowEvents', false);
      this.$store?.commit('setShowTasks', true);
      this.$store?.commit('setShowProjects', false);
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEditEvent', false);
      this.$store?.commit('setShowAddTask', false);
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowEditProject', false);
    },
    projectsPage() {
      this.$store?.commit('setShowEvents', false);
      this.$store?.commit('setShowTasks', false);
      this.$store?.commit('setShowProjects', true);
      this.$store?.commit('setShowAddEvent', false);
      this.$store?.commit('setShowEditEvent', false);
      this.$store?.commit('setShowAddTask', false);
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowEditProject', false);
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
  border-right: 3px solid gray;
  background-color: white;
  display: flex;
  align-content: stretch;
  flex-direction: column;
  justify-content: space-between;
}
.title {
  width: 85%;
  height: 6%;
  right: 0;
  top: 0;
  position: absolute;
  background-color: white;
  font-size: xxx-large;
  font-weight: bold;
  align-items: center;
  display: flex;
  justify-content: flex-end;
  border-bottom: 3px solid gray;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
.menu_button:hover{
  background-color: dodgerblue;
  color: white;
  padding: 10px 50px;
  font-weight: bold;
  border-radius: 35px;
  justify-content: center;
  font-size: x-large;
}
.menu_button{
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
  bottom:0;
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
}
</style>