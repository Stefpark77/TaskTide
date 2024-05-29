<template>
  <div v-if="this.$store?.state?.showAddTask">
    <div class="zoneAddTask">
      <div class="w-full max-w-xs mx-auto">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4">
          <div class="mb-4 mt-4">
            <input
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="name" type="text" placeholder="Name" v-model="addName"/>
          </div>
          <div class="description_container">
            <input
                class="description"
                id="description" type="text" placeholder="Description" v-model="addDescription"/>
          </div>
          <div class="mb-6">
            <input
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description" type="number" placeholder="Difficulty" v-model="addDifficulty"/>
          </div>
          <div class="mb-6">
            <label class="font-bold">Priority:</label>
            <select
                class="resize-y overflow-auto shadow appearance-none border rounded w-full py-3 px-4 text-lg text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="description"  v-model="addPriority">
              <option value="LOW">LOW</option>
              <option value="MEDIUM">MEDIUM</option>
              <option value="HIGH">HIGH</option>
            </select>
          </div>
          <div class="flex items-end">

            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="estimateDifficulty">
              Estimate Difficulty
            </button>
            <button
                class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                type="button" @click="addTask">
              Add Task
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
      addDifficulty: this.$store?.state?.addDifficulty ?? '',
      addPriority: this.$store?.state?.addPriority ?? '',
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
    addDifficulty: {
      handler: function (val) {
        this.$store?.commit('setAddDifficulty', val);
      },
      deep: true,
    },
    addPriority: {
      handler: function (val) {
        this.$store?.commit('setAddPriority', val);
      },
      deep: true,
    },
  },
  methods: {
    estimateDifficulty() {
      this.$store?.commit('estimateDifficulty', {text: this.addDescription});
      this.addDifficulty = this.$store?.state?.addDifficulty ?? '';
    },
    addTask() {
      this.$store?.commit('createTask', {name: this.addName, description: this.addDescription, difficulty: this.addDifficulty, priority: this.addPriority});
      this.$store?.commit('setShowAddTask', false);
      this.$store?.commit('setShowTasks', true);
    },
    cancelAdd() {
      this.$store?.commit('setShowAddTask', false);
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
</style>