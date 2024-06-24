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
                :rules="[value => value !== '' || 'This field is required.']"
                v-model="addName"
            ></v-text-field>
            <v-textarea id="description" rows="10" min-width="500px" type="text" placeholder="Description"
                        v-model="addDescription"
                        :rules="[value => value !== '' || 'This field is required.']"/>
          </div>
          <div class="mr-6">

            <v-select
                class="mb-5"
                :items="priorityValues"
                density="compact"
                label="Priority"
                :rules="[value => value !== '' || 'This field is required.']"
                v-model="addPriority"
            ></v-select>

            <v-text-field
                hide-details="auto"
                class="mb-5"
                type="number"
                label="Difficulty"
                :rules="[value => value >= 0 || 'No negative numbers allowed',
                value => value !== '' || 'This field is required.']"
                v-model="addDifficulty"
            ></v-text-field>
            <div class="flex items-end justify-end">
              <v-btn
                  :disabled="addDescription === '' "
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline mb-5
                   disabledFunction"
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
                  :disabled="addName === '' ||
             addDescription === '' ||
             addPriority === '' ||
             addDifficulty === null || addDifficulty === '' || addDifficulty < 0"
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline
                        disabledFunction"
                  type="button"
                  @click="addTask"
              >Add Task
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
      currentUser: '',
      addName: '',
      addDescription: '',
      addDifficulty: '',
      addPriority: 'LOW',
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
      this.$store.dispatch('estimateDifficulty', this.addDescription)
          .then((difficulty) => {
            this.addDifficulty = difficulty;
          })
          .catch((error) => {
            console.error('Error estimating difficulty:', error);
          });
    },
    addTask() {
      this.$store?.commit('createTask', {
        name: this.addName,
        description: this.addDescription,
        difficulty: this.addDifficulty,
        priority: this.addPriority,
        userId: this.currentUser.id,
      });
      this.$store?.commit('setShowAddTask', false);
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

.disabledFunction:disabled {
  background-color: dodgerblue;
  opacity: 75%;
}
</style>