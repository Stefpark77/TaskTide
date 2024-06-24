<template>
  <div v-if="this.$store?.state?.showEditTask">
    <div class="zoneAddTask">
      <div class="w-full max-w-xs mx-auto flex justify-center">
        <div class="bg-white shadow-md rounded-3xl px-8 pt-6 pb-8 mb-4 flex beautifulShadow">
          <div class="mr-6">
            <v-text-field
                hide-details="auto"
                class="mb-5"
                label="Name"
                v-model="updateName"
                :rules="[value => value !== '' || 'This field is required.']"
            ></v-text-field>
            <v-textarea id="description" rows="10" min-width="500px" type="text" placeholder="Description"
                        v-model="updateDescription"
                        :rules="[value => value !== '' || 'This field is required.']"/>
          </div>
          <div class="mr-6">

            <v-select
                class="mb-1"
                :items="priorityValues"
                density="compact"
                label="Priority"
                :rules="[value => value !== '' || 'This field is required.']"
                v-model="updatePriority"
            ></v-select>

            <v-select
                class="mb-1"
                :items="stages"
                density="compact"
                label="Stage"
                :rules="[value => value !== '' || 'This field is required.']"
                v-model="updateStage"
            ></v-select>

            <div v-if="updateStage === 'IN_PROGRESS'">
              <div class="text-caption">
                Progress (% DONE):
              </div>
              <v-slider
                  v-model="updateProgress"
                  :thumb-size="26"
                  thumb-label
                  :step="1"
              ></v-slider>
            </div>
            <v-text-field
                hide-details="auto"
                class="mb-5"
                type="number"
                label="Difficulty"
                :rules="[value => value >= 0 || 'No negative numbers allowed',
                value => value !== '' || 'This field is required.']"
                v-model="updateDifficulty"
            ></v-text-field>
            <div class="flex items-end justify-end">
              <v-btn
                  :disabled="updateDescription === '' "
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline mb-5
                  disabledFunction"
                  type="button" @click="estimateDifficulty">
                Estimate Difficulty
              </v-btn>
            </div>
            <div class="flex items-end justify-end">
              <v-btn
                  class="mr-5"
                  type="button" @click="cancelUpdate">
                Cancel
              </v-btn>

              <v-btn
                  class="mr-5 bg-red-300 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline"
                  type="button" @click="removeTask(editTaskId)">
                Delete
              </v-btn>

              <v-btn
                  :disabled="updateName === '' ||
             updateDescription === '' ||
             updatePriority === '' ||
             updateDifficulty === null || updateDifficulty === '' || updateDifficulty < 0"
                  class="left-0 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-2xl focus:outline-none focus:shadow-outline
                  disabledFunction"
                  type="button" @click="updateTask">
                Update
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
      editTaskId: this.$store?.state?.editTaskId ?? '',
      task: this.$store?.state?.task ?? '',
      updateName: this.$store?.state?.updateName ?? '',
      updateDescription: this.$store?.state?.updateDescription ?? '',
      updateDifficulty: this.$store?.state?.updateDifficulty ?? '',
      updatePriority: this.$store?.state?.updatePriority ?? '',
      updateStage: this.$store?.state?.updateStage ?? '',
      updateProgress: this.$store?.state?.updateProgress ?? '',
      priorityValues: ['LOW', 'MEDIUM', 'HIGH'],
      stages: ['TO_DO', 'IN_PROGRESS', 'COMPLETED'],
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
        progress: this.updateProgress,
        userId: this.task.userId,
        projectId: this.task.projectId,
        deadline: this.task.deadline,

      });
      this.$store?.commit('setShowEditTask', false);
    },
    cancelUpdate() {
      this.$store?.commit('setShowEditTask', false);
      this.$store?.commit('setShowTaskPage', true);
    },
    removeTask(id) {
      this.$store?.commit('removeTask', id);
      this.$store?.commit('setShowEditTask', false);
    },
    estimateDifficulty() {
      this.$store.dispatch('estimateDifficulty', this.updateDescription)
          .then((difficulty) => {
            this.updateDifficulty = difficulty;
          })
          .catch((error) => {
            console.error('Error estimating difficulty:', error);
          });
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