<template>
  <div v-if="this.$store?.state?.showProjects">
    <div class="zone">
      <div class="flex justify-end">
        <button
            class="mt-5 mr-10 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl border-gray-50 border-2 focus:outline-none focus:shadow-outline"
            type="button"
            v-on:click="addProject">
          Add New Project?
        </button>
      </div>
      <div class="projects">
        <div class="project" v-for="project in this.$store?.state?.projects" :key="project.id">
          <div class="name_and_button">
            <a class="font-bold ">{{ project.name }}</a>
            <button
                class="bg-white hover:bg-blue-700 text-black  hover:text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                type="button" @click="updateProject(project)">
              ...
            </button>
          </div>
          <div class="description_container">
            <div class="description">
              {{project.description.length > 255 ? project.description.slice(0, 255) + '...' : project.description }}
            </div>
          </div>
          <div class="deadlines">
            <a class="deadline_text"><a class="text-red-500">DEADLINE:</a> {{ format(parseISO(project.deadline),"MMMM dd, yyyy")}}</a>
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
      projects: this.$store?.state?.projects ?? [],
    };
  },
  methods: {
    parseISO,
    format,
    addProject() {
      this.$store?.commit('setShowAddProject', true);
      this.$store?.commit('setShowProjects', false);
    },
    updateProject(project) {
      this.$store?.commit('setUpdateProject', project);
      this.$store?.commit('setShowProjects', false);
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
.description_container{
  display: flex;
  justify-content: space-evenly;
  margin-bottom: 1%;
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
.projects {
  width: 100%;
  height: 90%;
  bottom: 0;
  right: 0;
  position: absolute;
  background-color: lightsteelblue;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(450px, 1fr)); /* Adjust the minimum and maximum width of each column */
  gap: 150px; /* Adjust the gap between cards */
  overflow: auto; /* Add scrollbar when needed */
}
.project {
  width: 400px; /* Adjust the width of your card */
  height: 300px; /* Adjust the height of your card */
  margin-right: 150px; /* Adjust the margin between cards */
  margin-left: 150px; /* Adjust the margin between cards */
  border: 1px solid #ccc;
  border-radius: 25px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  background-color: white;
  display: flex;
  align-content: stretch;
  flex-direction: column;
  justify-content: space-between;
}
</style>