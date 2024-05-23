<template>
  <div v-if="this.$store?.state?.showAddProject">
    <div class="zoneAddProject">
      <div class="w-full max-w-xs mx-auto">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4">
          <div class="mb-4 mt-4">
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name" type="text" placeholder="Name" v-model="addName"/>
          </div>
          <div class="mb-6">
            <input
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="text" placeholder="Description" v-model="addDescription"/>
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="deadline">
              Deadline:
            </label>
            <input
                class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="deadline" type="date" placeholder="Date" v-model="addDeadline"/>
          </div>
          <div class="flex items-end">
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="addProject">
              Add Project
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="cancelAdd">
              Cancel
            </button>
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