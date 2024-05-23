<template>
  <div v-if="this.$store?.state?.showEditProject">
    <div class="zoneAddProject">
      <div class="w-full max-w-xs mx-auto">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4">
          <div class="mb-4 mt-4">
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name" type="text" placeholder="Name" v-model="updateName"/>
          </div>
          <div class="mb-6">
            <input
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="text" placeholder="Description" v-model="updateDescription"/>
          </div>
          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="date">
              Deadline:
            </label>
            <input
                class="mb-6 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="date" type="date" placeholder="Date" v-model="updateDeadline"/>
          </div>
          <div class="flex items-end">
            <button
                class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="removeProject(editProjectId)">
              Delete
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="updateProject">
              Update
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="cancelUpdate">
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
          editProjectId: this.$store?.state?.editProjectId ?? '',
          updateName: this.$store?.state?.updateName ?? '',
          updateDescription: this.$store?.state?.updateDescription ?? '',
          updateDeadline: this.$store?.state?.updateDeadline ?? '',
        };
    },
    watch: {
        updateName: {
            handler: function (val) {
                this.$store?.commit('setUpdateName', val);
            },
            deep: true,
        },
        updateDescription: {
            handler: function (val) {
                this.$store?.commit('setUpdateDescription', val);
            },
            deep: true,
        },
        updateDeadline: {
          handler: function (val) {
            this.$store?.commit('setUpdateDate', val);
          },
          deep: true,
        },
    },
    methods: {
      updateProject() {
            this.$store?.commit('updateProject', { name: this.updateName, description: this.updateDescription,  deadline: this.updateDeadline});
            this.$store?.commit('setShowEditProject', false);
            this.$store?.commit('setShowProjects', true);
        },
      cancelUpdate() {
        this.$store?.commit('setShowEditProject', false);
        this.$store?.commit('setShowProjects', true);
      },
      removeProject(id) {
        this.$store?.commit('removeProject', id);
        this.$store?.commit('setShowEditProject', false);
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