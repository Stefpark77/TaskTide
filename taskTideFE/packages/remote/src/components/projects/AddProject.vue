<template>
  <div v-if="this.$store?.state?.showAddProject">
    <div class="zoneAddProject">
      <div class="w-full max-w-xs mx-auto flex justify-center">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4 flex beautifulShadow">
        <div class="mr-6">
          <v-text-field
              hide-details="auto"
              class="mb-5"
              label="Name"
              v-model="addName"
          ></v-text-field>
          <v-textarea id="description" rows="10" min-width="500px" type="text" placeholder="Description" v-model="addDescription" />

          <v-text-field type="date"
                        label = "Deadline"
                        v-model="addDeadline"
          ></v-text-field>

          <div class="flex items-end justify-end">
            <v-btn
                class="mr-5"
                type="button" @click="cancelAdd">
              Cancel
            </v-btn>
            <v-btn
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="addProject">
              Add Project
            </v-btn>
          </div>
        </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addName: this.$store?.state?.addName ?? '',
      addDescription: this.$store?.state?.addDescription ?? '',
      addDeadline: this.$store?.state?.addDeadline ?? '',
    };
  },
  watch: {
    addName: {
      handler: function (val) {
        this.$store?.commit('setAddName', val);
      },
      deep: true,
    },
    addDescription: {
      handler: function (val) {
        this.$store?.commit('setAddDescription', val);
      },
      deep: true,
    },
    addDeadline: {
      handler: function (val) {
        this.$store?.commit('setAddDeadline', val);
      },
      deep: true,
    },
  },
  methods: {
    addProject() {
      this.$store?.commit('createProject', {name: this.addName, description: this.addDescription, deadline: this.addDeadline});
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowProjects', true);
    },
    cancelAdd() {
      this.$store?.commit('setShowAddProject', false);
      this.$store?.commit('setShowProjects', true);
    },

  },
};
</script>

<style>

.zoneAddProject {
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
}

</style>