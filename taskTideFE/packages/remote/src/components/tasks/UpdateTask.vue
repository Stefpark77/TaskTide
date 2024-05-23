<template>
  <div v-if="this.$store?.state?.showEditTask">
    <div class="zoneAddTask">
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
            <label class="font-bold">Difficulty:</label>
            <input
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="number" placeholder="Difficulty" v-model="updateDifficulty"/>
          </div>
          <div class="mb-6">
            <label class="font-bold">Progress:</label>
            <input
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="number" placeholder="Progress" v-model="updateProgress"/>
          </div>
          <div class="mb-6">
            <label class="font-bold">Priority:</label>
            <select
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" v-model="updatePriority">
              <option value="LOW">LOW</option>
              <option value="MEDIUM">MEDIUM</option>
              <option value="HIGH">HIGH</option>
            </select>
          </div>

          <div class="mb-6">
            <label class="font-bold">Priority:</label>
            <select
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" v-model="updateStage">
              <option value="TO_DO">TO_DO</option>
              <option value="IN_PROGRESS">IN_PROGRESS</option>
              <option value="COMPLETED">COMPLETED</option>
            </select>
          </div>

          <div class="flex items-end">
            <button
                class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="removeTask(editTaskId)">
              Delete
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="updateTask">
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
      editTaskId: this.$store?.state?.editTaskId ?? '',
      updateName: this.$store?.state?.updateName ?? '',
      updateDescription: this.$store?.state?.updateDescription ?? '',
      updateDifficulty: this.$store?.state?.updateDifficulty ?? '',
      updatePriority: this.$store?.state?.updatePriority ?? '',
      updateStage: this.$store?.state?.updateStage ?? '',
      updateProgress: this.$store?.state?.updateProgress ?? '',
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
    updateDifficulty: {
      handler: function (val) {
        this.$store?.commit('setUpdateDifficulty', val);
      },
      deep: true,
    },
    updatePriority: {
      handler: function (val) {
        this.$store?.commit('setUpdatePriority', val);
      },
      deep: true,
    },
    updateStage: {
      handler: function (val) {
        this.$store?.commit('setUpdateStage', val);
      },
      deep: true,
    },
    updateProgress: {
      handler: function (val) {
        this.$store?.commit('setUpdateProgress', val);
      },
      deep: true,
    },
  },
  methods: {
    updateTask() {
      this.$store?.commit('updateTask', {
        name: this.updateName,
        description: this.updateDescription,
        difficulty: this.updateDifficulty,
        priority: this.updatePriority,
        stage: this.updateStage,
        progress: this.updateProgress
      });
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTasks', true);
    },
    cancelUpdate() {
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTasks', true);
    },
    removeTask(id) {
      this.$store?.commit('removeTask', id);
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTasks', true);
    },
  },
};
</script>


<style>

.zoneAddTask {
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