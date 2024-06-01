<template>
  <div v-if="this.$store?.state?.showAddTask">
    <div class="zoneAddTask">
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
          </div>
          <div class="mr-6">

            <v-select
                class="mb-5"
                :items="priorityValues"
                density="compact"
                label="Priority"
                v-model = "addPriority"
            ></v-select>

            <v-text-field
                hide-details="auto"
                class="mb-5"
                type="number"
                label="Difficulty"
                :rules="[value => value >= 0 || 'No negative numbers allowed']"
                v-model="addDifficulty"
            ></v-text-field>
            <div class="flex items-end justify-end">
              <v-btn
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline mb-5"
                  type="button" @click="estimateDifficulty">
                Estimate Difficulty
              </v-btn>
            </div>
            <div class="flex items-end justify-end">
              <v-btn
                  class="mr-5"
                  type="button" @click="cancelAdd">
                Cancel
              </v-btn>
              <v-btn
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" @click="addTask">
                Add Task
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
      addDifficulty: this.$store?.state?.addDifficulty ?? '',
      addPriority: this.$store?.state?.addPriority ?? 'LOW',
      priorityValues: ['LOW', 'MEDIUM', 'HIGH']
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