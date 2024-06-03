<template>
  <div v-if="this.$store?.state?.showProjects">
    <div class="zone">
      <div class="flex justify-start bg-white">
        <v-tabs>
          <v-text-field
              class="ml-5 border-r-2 border-l-2"
              width="300px"
              prepend-inner-icon="mdi-magnify"
              placeholder="Search By Name"
              v-model="searchBar"
          ></v-text-field>
        </v-tabs>
        <v-spacer></v-spacer>
        <v-spacer></v-spacer>
          <v-spacer></v-spacer>
          <v-tabs>
            <v-tab
                prepend-icon="mdi-file-document-plus-outline"
                class="menu_button"
                v-on:click="addProject">
              New Project
            </v-tab>
          </v-tabs>
      </div>
      <div class="projects">

        <v-card class="project" v-for="project in this.$store?.state?.projects.filter(p => searchBar === '' || p.name.includes(searchBar))" :key="project.id">
          <v-card-item>
            <v-card-title class="text-body-2 d-flex align-center ">

              <div class="text-h6"><a class="font-bold"><v-icon
                  color="#1e90ff"
                  icon="mdi-text-box-multiple-outline"
                  start
              ></v-icon>{{ project.name }}</a></div>
              <v-spacer></v-spacer>

              <v-chip
                  class="ms-2 text-medium-emphasis"
                  prepend-icon="mdi-eye"
                  color="#1e90ff"
                  size="small"
                  text="OPEN"
                  variant="outlined"
                  @click="openProject(project)"
              ></v-chip>

            </v-card-title>

            <div class="py-2">
              <div class="text-h6 border-b-2"><a class="font-bold"></a></div>

              <div class="font-weight-light text-medium-emphasis">
                {{ project.description.length > 255 ? project.description.slice(0, 255) + '...' : project.description }}
              </div>
            </div>
          </v-card-item>
          <v-card-item class="deadlines">
            <a class="deadline_text"><a class="text-red-500">DEADLINE:</a>
              {{ format(parseISO(project.deadline), "MMMM dd, yyyy") }}</a>
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
      projects: this.$store?.state?.projects ?? [],
      searchBar: '',
    };
  },
  methods: {
    parseISO,
    format,
    addProject() {
      this.$store?.commit('setShowAddProject', true);
      this.$store?.commit('setShowProjects', false);
    },
    openProject(project) {
      this.$store?.commit('setProjectPage', project);
      this.$store?.commit('setShowProjects', false);
      this.$store?.commit('fetchTaskByProjectId', project.id);
      this.$store?.commit('fetchUsersForProject', project.id);
      this.$store?.commit('fetchAllTasks', true);
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

.deadlines {
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;
}

.deadline_text {
  font-size: large;
  font-weight: bold;
}

.name_and_button {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1%;
  font-size: x-large;
}

.projects {
  width: 100%;
  height: 96%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(500px, 1fr)); /* Adjust the minimum and maximum width of each column */
  gap: 100px; /* Adjust the gap between cards */
  overflow: auto; /* Add scrollbar when needed */
}

.project {
  width: 400px; /* Adjust the width of your card */
  height: 300px; /* Adjust the height of your card */
  margin-top: 100px;
  margin-right: 150px; /* Adjust the margin between cards */
  margin-left: 150px; /* Adjust the margin between cards */
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
</style>