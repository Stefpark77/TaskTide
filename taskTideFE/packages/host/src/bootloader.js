import {createApp} from "vue";
import {createStore} from "vuex";
import * as VueRouter from "vue-router";

import {login, signUp} from "./calls/authServicesCalls";
import {
    getAllEvents,
    getEventsByUserId,
    getEventByEventId,
    updateEvent,
    deleteEvent,
    createEvent, getEventsByUserIdAndDay
} from "./calls/calendarServicesCalls";
import {
    getAllUsers,
    getUserByUserId,
    getUserByUsername,
    createUser,
    deleteUser,
    updateUser, getUserByProjectId
} from "./calls/userServicesCalls";
import {
    getAllTasks,
    getTaskByTaskId,
    getTaskByUserId,
    createTask,
    deleteTask,
    updateTask,
    getTaskDependencyByTaskId,
    getTaskDependencyByDependsOnId,
    createTaskDependency,
    deleteTaskDependency, getTaskByProjectId,
} from "./calls/taskServicesCalls";
import {
    getAllProjects,
    getProjectByProjectId,
    getProjectByUserId,
    createProject,
    deleteProject,
    updateProject, createProjectUser, deleteProjectUser
} from "./calls/projectServicesCalls";

import "./index.scss";
import App from "./App.vue";

import LoginPage from "./views/LoginPage.vue";
import MenuPage from "./views/MenuPage.vue";

import {format, parseISO} from 'date-fns';
import {getEstimatedDifficulty} from "./calls/difficultyEstimatorCalls";

const routes = [
    {path: "/", component: LoginPage},
    {path: "/tasktide", component: MenuPage},
];

const router = VueRouter.createRouter({
    history: VueRouter.createWebHistory(),
    routes,
});


const checkAuth = async (response) => {
    if (response?.status === 500 || !response) {
        window.location.href = '/';
    }
}

const store = createStore({
    state() {
        return {
            /*auth services*/
            username: '',
            password: '',
            loginError: '',
            showSignUp: false,
            addFirstName: '',
            addLastName: '',
            addUsername: '',
            addPassword: '',
            addCPassword: '',
            addEmail: '',
            token: '',
            /*calendar services*/
            eventsWeek: [],
            showEvents: false,
            showAddEvent: false,
            showEditEvent: false,
            /*Add Event*/
            addName: '',
            addDescription: '',
            addDate: '',
            addDateTime: '',
            addEndDate: '',
            addEndDateTime: '',
            /*Edit Event*/
            editEventId: '',
            updateName: '',
            updateDescription: '',
            updateDate: '',
            updateDateTime: '',
            updateEndDate: '',
            updateEndDateTime: '',
            updateRecurring: '',
            /*task services*/
            tasks: [],
            allTasks: [],
            showTasks: false,
            showAddTask: false,
            showEditTask: false,
            showTaskPage: false,
            /*Add/Edit Task*/
            editTaskId: '',
            addDifficulty: '',
            addPriority: '',
            updateDifficulty: '',
            updatePriority: '',
            updateStage: '',
            updateProgress: '',
            /*Task Page*/
            task: '',
            taskDependencies: [],
            taskDependenciesOn: [],
            taskDependenciesIds: [],
            taskDependenciesOnIds: [],
            /*project services*/
            projects: [],
            showProjects: false,
            showAddProject: false,
            showEditProject: false,
            showProjectPage: false,
            /*Add/Edit Project*/
            editProjectId: '',
            addDeadline: '',
            updateDeadline: '',
            /*Project Page*/
            project: '',
            projectTasks: [],
            projectUsers: [],
            currentUser: '',
        };
    },
    mutations: {
        /*Auth Services*/
        async login(state) {
            try {
                const response = await login({username: state.username, password: state.password});

                if (response.status !== 200 || response.data === '') throw new Error('Failed to login');
                state.loginError = false;
                localStorage.setItem("username", response.data.username)
                localStorage.setItem("email", response.data.email)
                localStorage.setItem("userId", response.data.userId)
                localStorage.setItem("token", response.data.token)
                window.location.href = '/tasktide';
            } catch (error) {
                console.log(error);
                state.loginError = true;
            }
        },
        async signUp(state, user) {
            try {
                if (user.cPassword !== user.password) throw new Error('The Password fields must match!');
                delete user.cPassword;
                const response = await signUp(user);

                if (response.status !== 200) throw new Error('Failed to sign up user');

            } catch (error) {
                console.error(error);
            }

            state.addFirstName = '';
            state.addLastName = '';
            state.addUsername = '';
            state.addPassword = '';
            state.addCPassword = '';
            state.addEmail = '';
            state.showSignUp = false;
        },
        async logout() {
            try {
                localStorage.setItem("userId", '')
                localStorage.setItem("token", '')
                window.location.href = '/';
            } catch (error) {
                console.log(error);
            }
        },
        async prepareMenu(state) {
            try {
                if(state.token===''){
                    state.token = localStorage.getItem("token");
                }
                if(state.currentUser===''){
                    state.currentUser= {id: localStorage.getItem("userId")}
                }
                const response = await getUserByUserId(state.currentUser.id, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get current user');

                state.currentUser = response.data;
            } catch (error) {
                console.error(error);
            }

        },
        /*Calendar Services*/
        async fetchWeekEvents(state, firstDay) {
            try {
                if(state.token===''){
                    state.token = localStorage.getItem("token");
                }
                if(state.currentUser===''){
                    state.currentUser = {id: localStorage.getItem("userId")}
                }
                var curr;
                var currentDate;
                if (firstDay === null) {
                    curr = new Date;
                    const currentDay = curr.getDate() - curr.getDay() + 1;
                    currentDate = new Date(curr.setDate(currentDay));
                } else {
                    currentDate= firstDay;
                }
                let index = 0;
                while (index < 7) {
                    const day = format(currentDate, "yyyy-MM-dd'T'HH:mm:ss'Z'");
                    const response2 = await getEventsByUserIdAndDay(state.currentUser.id, day, state.token);
                    checkAuth(response2);
                    state.eventsWeek[index] = response2.data;
                    currentDate = new Date(currentDate);
                    currentDate.setDate(currentDate.getDate() + 1);
                    index++;
                }
            } catch (error) {
                console.error(error);
            }
        },
        async removeEvent(state, eventId) {
            try {
                const response = await deleteEvent(eventId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete event');
                await this.commit("fetchWeekEvents", null)
            } catch (error) {
                console.error(error);
            }
        },
        async createEvent(state, event) {
            console.log(event);
            try {
                if (event.fullDay) {
                    const dateObject = parseISO(event.date);
                    dateObject.setHours(0);
                    dateObject.setMinutes(0);
                    event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    dateObject.setHours(23);
                    dateObject.setMinutes(59);
                    event.endDate = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    delete event.fullDay;
                } else {
                    const dateObject = parseISO(event.date);
                    event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    const endDateObject = parseISO(event.endDate);
                    event.endDate = format(endDateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    delete event.fullDay;
                }

                event.userId = state.currentUser.id
                console.log(event);

                const response = await createEvent(event, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create event');
                await this.commit("fetchWeekEvents", null)

            } catch (error) {
                console.error(error);
            }

            state.addName = '';
            state.addDescription = '';
            state.addDate = '';
            state.addEndDate = '';
            state.addDateTime = '';
            state.addEndDateTime = '';
        },
        setUpdateEvent(state, event) {
            state.showEditEvent = true;
            state.editEventId = event.id;
            state.updateName = event.name;
            state.updateDescription = event.description;
            state.updateDate = format(parseISO(event.date), "yyyy-MM-dd'T'HH:mm:ss");
            state.updateEndDate = format(parseISO(event.endDate), "yyyy-MM-dd'T'HH:mm:ss");

            if (parseISO(state.updateDate).getDate() === parseISO(state.updateEndDate).getDate()
            && parseISO(state.updateDate).getHours() === 0 && parseISO(state.updateDate).getMinutes() === 0
            && parseISO(state.updateEndDate).getHours() === 23 && parseISO(state.updateEndDate).getMinutes() === 59){
                state.updateDate = format(parseISO(event.date), "yyyy-MM-dd");
            }
            state.updateRecurring = event.recurringTime;

        },
        async updateEvent(state, event) {
            try {
                event.id = state.editEventId;
                if (event.fullDay) {
                    const dateObject = parseISO(event.date);
                    dateObject.setHours(0);
                    dateObject.setMinutes(0);
                    event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    dateObject.setHours(23);
                    dateObject.setMinutes(59);
                    event.endDate = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    delete event.fullDay;
                } else {
                    const dateObject = parseISO(event.date);
                    event.date = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    const endDateObject = parseISO(event.endDate);
                    event.endDate = format(endDateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                    delete event.fullDay;
                }

                event.userId = state.currentUser.id

                console.log(event);

                const response = await updateEvent(event, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update event');
                state.showEditEvent = false;
                await this.commit("fetchWeekEvents", null)
            } catch (error) {
                console.error(error);
            }
        },

        /*Task Services*/

        async fetchAllTasks(state) {
            try {
                const response = await getAllTasks(state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get tasks');

                state.allTasks = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTasks(state, showTasks) {
            try {
                if(state.token===''){
                    state.token = localStorage.getItem("token");
                }
                if(state.currentUser===''){
                    state.currentUser= {id: localStorage.getItem("userId")}
                }
                const responseProjects = await getProjectByUserId(state.currentUser.id, state.token);

                await checkAuth(responseProjects);

                if (responseProjects.status !== 200) throw new Error('Failed to get enrolled Projects');
                let projectIds = responseProjects.data.map(projectUser => projectUser.projectId);


                const response = await getTaskByUserId(state.currentUser.id, projectIds, state.token);

                await checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get tasks');

                state.tasks = response.data;
                let updatedTasks = [];
                for (let task of state.tasks) {
                    if (task.projectId !== null) {
                        const projectResponse = await getProjectByProjectId(task.projectId, state.token);
                        task.project = projectResponse.data;
                    } else {
                        task.project = null;
                    }
                    if (task.userId !== null) {
                        const userResponse = await getUserByUserId(task.userId, state.token);
                        task.user = userResponse.data;
                    } else {
                        task.user = null;
                    }
                    updatedTasks.push(task);
                }
                state.tasks = updatedTasks;
                if(showTasks === true){
                    state.showTasks = true;
                }
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskByTaskId(state, taskId) {
            try {
                const response = await getTaskByTaskId(localStorage.getItem("taskId"), state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task by taskId');

                return response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskByProjectId(state, projectId) {
            try {
                const response = await getTaskByProjectId(projectId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task by projectId');

                state.projectTasks = response.data;

                let updatedTasks = [];
                for (let task of state.projectTasks) {
                    if (task.projectId !== null) {
                        const projectResponse = await getProjectByProjectId(task.projectId, state.token);
                        task.project = projectResponse.data;
                    } else {
                        task.project = null;
                    }
                    if (task.userId !== null) {
                        const userResponse = await getUserByUserId(task.userId, state.token);
                        task.user = userResponse.data;
                    } else {
                        task.user = null;
                    }
                    updatedTasks.push(task);
                }
                state.projectTasks = updatedTasks;
            } catch (error) {
                console.error(error);
            }
        },
        async removeTask(state, taskId) {
            try {
                const response = await deleteTask(taskId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete task');

                await this.commit("fetchTasks", true)
            } catch (error) {
                console.error(error);
            }
        },
        async createTask(state, task) {
            console.log(task);
            try {
                task.userId = state.currentUser.id
                console.log(task);

                const response = await createTask(task, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task');
                var taskResponse = response.data;
                taskResponse.user = state.currentUser;

                await this.commit("fetchTasks", true)
            } catch (error) {
                console.error(error);
            }

            state.addName = '';
            state.addDescription = '';
            state.addDate = '';
            state.addEndDate = '';
            state.addDateTime = '';
            state.addEndDateTime = '';
        },
        setUpdateTask(state, task) {
            state.showEditTask = true;
            state.editTaskId = task.id;
            state.updateName = task.name;
            state.updateDescription = task.description;
            state.updateDifficulty = task.difficulty;
            state.updatePriority = task.priority;
            state.updateStage = task.stage;
            state.updateProgress = task.progress;

        },
        async updateTask(state, task) {
            try {
                console.log(task);
                task.id = state.task.id;
                task.projectId = state.task.projectId;

                const response = await updateTask(task, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update task');
                var updatedTask = response.data;
                if (updatedTask.userId !== null) {
                    const userResponse = await getUserByUserId(updatedTask.userId, state.token);
                    updatedTask.user = userResponse.data;
                } else {
                    updatedTask.user = null;
                }
                if (updatedTask.projectId !== null) {
                    const projectResponse = await getProjectByProjectId(updatedTask.projectId, state.token);
                    updatedTask.project = projectResponse.data;
                } else {
                    updatedTask.project = null;
                }

                await this.commit("fetchTasks", true)

            } catch (error) {
                console.error(error);
            }
        },
        setTaskPage(state, task) {
            state.showTaskPage = true;
            state.task = task;
        },
        /*TaskDependency Services*/
        async fetchTaskDependenciesByTaskId(state, taskId) {
            try {
                const response = await getTaskDependencyByTaskId(taskId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task dependencies');

                state.taskDependencies = response.data;
                let updatedTasks = [];
                for (let task of state.taskDependencies) {
                    if (task.projectId !== null) {
                        const projectResponse = await getProjectByProjectId(task.projectId, state.token);
                        task.project = projectResponse.data;
                    } else {
                        task.project = null;
                    }
                    if (task.userId !== null) {
                        const userResponse = await getUserByUserId(task.userId, state.token);
                        task.user = userResponse.data;
                    } else {
                        task.user = null;
                    }

                    updatedTasks.push(task);
                }
                state.taskDependencies = updatedTasks;
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskDependenciesByDependsOnId(state, dependsOnId) {
            try {
                const response = await getTaskDependencyByDependsOnId(dependsOnId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task dependencies');

                state.taskDependenciesOn = response.data;
                let updatedTasks = [];
                for (let task of state.taskDependenciesOn) {
                    if (task.projectId !== null) {
                        const projectResponse = await getProjectByProjectId(task.projectId, state.token);
                        task.project = projectResponse.data;
                    } else {
                        task.project = null;
                    }
                    if (task.userId !== null) {
                        const userResponse = await getUserByUserId(task.userId, state.token);
                        task.user = userResponse.data;
                    } else {
                        task.user = null;
                    }

                    updatedTasks.push(task);
                }
                state.taskDependenciesOn = updatedTasks;
            } catch (error) {
                console.error(error);
            }
        },
        async removeTaskDependency(state, dependency) {
            try {
                const response = await deleteTaskDependency(dependency.taskId, dependency.dependsOnId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete task dependency');
                await this.commit("fetchTaskDependenciesByDependsOnId",state.task.id)
                await this.commit("fetchTaskDependenciesByTaskId",state.task.id)


            } catch (error) {
                console.error(error);
            }
        },
        async createTaskDependency(state, dependency) {
            try {
                const response = await createTaskDependency(dependency, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task dependency');
                await this.commit("fetchTaskDependenciesByDependsOnId",state.task.id)
                await this.commit("fetchTaskDependenciesByTaskId",state.task.id)

            } catch (error) {
                console.error(error);
            }
        },

        /*Project Services*/
        async fetchProjects(state) {
            if(state.token===''){
                state.token = localStorage.getItem("token");
            }
            try {
                const response = await getAllProjects(state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get projects');
                state.projects = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeProject(state, projectId) {
            try {
                const response = await deleteProject(projectId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete project');
                await this.commit("fetchProjects", null)
            } catch (error) {
                console.error(error);
            }
        },
        async createProject(state, project) {
            console.log(project);
            try {
                const dateObject = parseISO(project.deadline);
                project.deadline = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                const response = await createProject(project, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create project');
                await this.commit("fetchProjects", null)
            } catch (error) {
                console.error(error);
            }

            state.addName = '';
            state.addDescription = '';
            state.addDeadline = '';
        },
        setUpdateProject(state, project) {
            state.showEditProject = true;
            state.editProjectId = project.id;
            state.updateName = project.name;
            state.updateDescription = project.description;
            state.updateDeadline = format(parseISO(project.deadline), "yyyy-MM-dd");
        },
        async updateProject(state, project) {
            try {
                project.id = state.editProjectId;
                const dateObject = parseISO(project.deadline);
                project.deadline = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");

                console.log(project);

                const response = await updateProject(project, state.token);

                checkAuth(response);
                if (response.status !== 200) throw new Error('Failed to update project');
                await this.commit("fetchProjects", null)

                state.showEditProject = false;
            } catch (error) {
                console.error(error);
            }
        },
        setProjectPage(state, project) {
            state.showProjectPage = true;
            state.project = project;
        },
        async updateTaskForProject(state, taskAndProject) {
            try {
                taskAndProject.task.projectId = taskAndProject.project.id;
                taskAndProject.task.deadline = taskAndProject.project.deadline;
                console.log(taskAndProject.task);
                const response = await updateTask(taskAndProject.task, state.token);
                checkAuth(response);
                if (response.status !== 200) throw new Error('Failed to update task');
                await this.commit("fetchTaskByProjectId", taskAndProject.projectId);
                await this.commit('fetchAllTasks', true);
            } catch (error) {
                console.error(error);
            }
        },
        async fetchUsersForProject(state, projectId) {
            try {
                const response = await getUserByProjectId(projectId, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get users by projectId');

                state.projectUsers = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeProjectUser(state, projectUser) {
            try {
                const response = await deleteProjectUser(projectUser.projectId, state.currentUser.id, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete project user connection');

                const response2 = await getUserByProjectId(projectUser.projectId, state.token);
                checkAuth(response2);
                if (response2.status !== 200) throw new Error('Failed to get project users');
                state.projectUsers = response2.data;

            } catch (error) {
                console.error(error);
            }
        },
        async createProjectUser(state, projectUser) {
            try {
                projectUser.userId = state.currentUser.id;
                const response = await createProjectUser(projectUser, state.token);

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create project user connection');

                const response2 = await getUserByProjectId(projectUser.projectId, state.token);
                checkAuth(response2);
                if (response2.status !== 200) throw new Error('Failed to get project users');
                state.projectUsers = response2.data;

            } catch (error) {
                console.error(error);
            }
        },

        /*setters*/
        setUsername(state, username) {
            state.username = username;
        },
        setPassword(state, password) {
            state.password = password;
        },
        setShowEvents(state, showEvents) {
            state.showEvents = showEvents;
        },
        setShowTasks(state, showTasks) {
            state.showTasks = showTasks;
        },
        setShowProjects(state, showProjects) {
            state.showProjects = showProjects;
        },
        setShowAddEvent(state, showAddEvent) {
            state.showAddEvent = showAddEvent;
        },
        setShowAddTask(state, showAddTask) {
            state.showAddTask = showAddTask;
        },
        setShowAddProject(state, showAddProject) {
            state.showAddProject = showAddProject;
        },
        setShowSignUp(state, showSignUp) {
            state.showSignUp = showSignUp;
        },
        setShowEditEvent(state, showEditEvent) {
            state.showEditEvent = showEditEvent;
        },
        setShowEditTask(state, showEditTask) {
            state.showEditTask = showEditTask;
        },
        setShowEditProject(state, showEditProject) {
            state.showEditProject = showEditProject;
        },
        setAddName(state, addName) {
            state.addName = addName;
        },
        setAddDescription(state, addDescription) {
            state.addDescription = addDescription;
        },
        setAddDate(state, addDate) {
            state.addDate = addDate;
        },
        setAddDateTime(state, addDateTime) {
            state.addDateTime = addDateTime;
        },
        setAddEndDate(state, addEndDate) {
            state.addEndDate = addEndDate;
        },
        setAddEndDateTime(state, addEndDateTime) {
            state.addEndDateTime = addEndDateTime;
        },
        setUpdateName(state, updateName) {
            state.updateName = updateName;
        },
        setUpdateDescription(state, updateDescription) {
            state.updateDescription = updateDescription;
        },
        setUpdateDate(state, updateDate) {
            state.updateDate = updateDate;
        },
        setUpdateDateTime(state, updateDateTime) {
            state.updateDateTime = updateDateTime;
        },
        setUpdateEndDate(state, updateEndDate) {
            state.updateEndDate = updateEndDate;
        },
        setUpdateEndDateTime(state, updateEndDateTime) {
            state.updateEndDateTime = updateEndDateTime;
        },
        setAddFirstName(state, addFirstName) {
            state.addFirstName = addFirstName;
        },
        setAddLastName(state, addLastName) {
            state.addLastName = addLastName;
        },
        setAddUsername(state, addUsername) {
            state.addUsername = addUsername;
        },
        setAddPassword(state, addPassword) {
            state.addPassword = addPassword;
        },
        setAddCPassword(state, addCPassword) {
            state.addCPassword = addCPassword;
        },
        setAddEmail(state, addEmail) {
            state.addEmail = addEmail;
        },
        setAddDeadline(state, addDeadline) {
            state.addDeadline = addDeadline;
        },
        setUpdateDeadline(state, updateDeadline) {
            state.updateDeadline = updateDeadline;
        },
        setAddDifficulty(state, addDifficulty) {
            state.addDifficulty = addDifficulty;
        },
        setAddPriority(state, addPriority) {
            state.addPriority = addPriority;
        },
        setUpdateDifficulty(state, updateDifficulty) {
            state.updateDifficulty = updateDifficulty;
        },
        setUpdatePriority(state, updatePriority) {
            state.updatePriority = updatePriority;
        },

        setUpdateProgress(state, updateProgress) {
            state.updateProgress = updateProgress;
        },
        setUpdateStage(state, updateStage) {
            state.updateStage = updateStage;
        },
        setShowProjectPage(state, showProjectPage) {
            state.showProjectPage = showProjectPage;
        },
        setShowTaskPage(state, showTaskPage) {
            state.showTaskPage = showTaskPage;
        },
    },
    actions:{
        async estimateDifficulty({commit}, description) {
            try {
                const response = await getEstimatedDifficulty({text: description});
                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task dependency');
                console.log(response.data)
                return response.data.difficulty;
            } catch (error) {
                console.error(error);
            }
        },
    }
});

import {createVuetify} from 'vuetify';
import 'vuetify/dist/vuetify.min.css'; //import Vuetify styles
import './index.scss'; // import your custom styles
import 'vuetify/styles'; // Vuetify 3 specific import
import "@mdi/font/css/materialdesignicons.css";
import {aliases, mdi} from "vuetify/lib/iconsets/mdi.mjs";
import {components, directives} from "vuetify/dist/vuetify";

const vuetify = createVuetify({
    components,
    directives,
    icons: {
        defaultSet: "mdi",
        aliases,
        sets: {
            mdi,
        },
    },
});
const app = createApp(App);
app.use(router);
app.use(store);
app.use(vuetify);
app.mount('#app');
