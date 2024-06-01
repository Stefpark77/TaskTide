import {createApp} from "vue";
import {createStore} from "vuex";
import * as VueRouter from "vue-router";

import {login, signUp, logout} from "./calls/authServicesCalls";
import {
    getAllEvents,
    getEventsByUserId,
    getEventByEventId,
    updateEvent,
    deleteEvent,
    createEvent
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
        //window.location.href = '/';
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
            events: [],
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
                const response = await logout();

                localStorage.setItem("userId", '')
                localStorage.setItem("token", '')
                window.location.href = '/';
            } catch (error) {
                console.log(error);
            }
        },
        async prepareMenu(state) {
            try {
                const response = await getUserByUserId(localStorage.getItem("userId"), localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get current user');

                state.currentUser = response.data;
            } catch (error) {
                console.error(error);
            }

        },

        /*Calendar Services*/
        async fetchEvents(state) {
            try {
                const response = await getEventsByUserId(localStorage.getItem("userId"), localStorage.getItem("token"));
                state.token = localStorage.getItem("token");
                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get events');

                state.events = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeEvent(state, eventId) {
            try {
                const response = await deleteEvent(eventId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete event');

                state.events = state.events.filter(event => event.id !== eventId);
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

                event.userId = localStorage.getItem("userId")
                console.log(event);

                const response = await createEvent(event, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create event');

                state.events.push(response.data);
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

                event.userId = localStorage.getItem("userId")

                console.log(event);

                const response = await updateEvent(event, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update event');

                state.events = state.events.map(e => e.id === event.id ? response.data : e);

                state.showEditEvent = false;
            } catch (error) {
                console.error(error);
            }
        },

        /*Task Services*/

        async fetchAllTasks(state) {
            try {
                const response = await getAllTasks(localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get tasks');

                state.allTasks = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTasks(state) {
            try {
                const response = await getTaskByUserId(localStorage.getItem("userId"), localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get tasks');

                state.tasks = response.data;
                let updatedTasks = [];
                for(let task of state.tasks){
                    if(task.projectId!==null){
                        const projectResponse = await getProjectByProjectId(task.projectId, localStorage.getItem("token"));
                        task.project = projectResponse.data;
                    }else{
                        task.project = null;
                    }
                    updatedTasks.push(task);
                }
                state.tasks = updatedTasks;
                console.log( state.tasks);
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskByTaskId(state, taskId) {
            try {
                const response = await getTaskByTaskId(localStorage.getItem("taskId"), localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task by taskId');

                return response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskByProjectId(state, projectId) {
            try {
                const response = await getTaskByProjectId(projectId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task by projectId');

                state.projectTasks = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeTask(state, taskId) {
            try {
                const response = await deleteTask(taskId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete task');

                state.tasks = state.tasks.filter(task => task.id !== taskId);
            } catch (error) {
                console.error(error);
            }
        },
        async createTask(state, task) {
            console.log(task);
            try {
                task.userId = localStorage.getItem("userId")
                console.log(task);

                const response = await createTask(task, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task');

                state.tasks.push(response.data);
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
                task.id = state.editTaskId;
                task.projectId = state.task.projectId;

                const response = await updateTask(task, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update task');

                state.tasks = state.tasks.map(e => e.id === task.id ? response.data : e);

                state.showEditTask = false;
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
                const response = await getTaskDependencyByTaskId(taskId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task dependencies');

                state.taskDependencies = response.data;
                console.log(state.taskDependencies);
            } catch (error) {
                console.error(error);
            }
        },
        async fetchTaskDependenciesByDependsOnId(state, dependsOnId) {
            try {
                const response = await getTaskDependencyByDependsOnId(dependsOnId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get task dependencies');

                state.taskDependenciesOn = response.data;
                console.log(state.taskDependenciesOn);
            } catch (error) {
                console.error(error);
            }
        },
        async removeTaskDependency(state, dependency) {
            try {
                const response = await deleteTaskDependency(dependency.taskId, dependency.dependsOnId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete task dependency');

                const response2 = await getTaskDependencyByDependsOnId(state.task.id, localStorage.getItem("token"));
                checkAuth(response2);
                if (response2.status !== 200) throw new Error('Failed to get task dependencies');
                state.taskDependenciesOn = response2.data;

                const response3 = await getTaskDependencyByTaskId(state.task.id, localStorage.getItem("token"));
                checkAuth(response3);
                if (response3.status !== 200) throw new Error('Failed to get task dependencies');
                state.taskDependencies = response3.data;

            } catch (error) {
                console.error(error);
            }
        },
        async createTaskDependency(state, dependency) {
            try {
                const response = await createTaskDependency(dependency, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task dependency');

                const response2 = await getTaskDependencyByDependsOnId(state.task.id, localStorage.getItem("token"));
                checkAuth(response2);
                if (response2.status !== 200) throw new Error('Failed to get task dependencies');
                state.taskDependenciesOn = response2.data;

                const response3 = await getTaskDependencyByTaskId(state.task.id, localStorage.getItem("token"));
                checkAuth(response3);
                if (response3.status !== 200) throw new Error('Failed to get task dependencies');
                state.taskDependencies = response3.data;
            } catch (error) {
                console.error(error);
            }
        },

        async estimateDifficulty(state, description) {
            try {
                const response = await getEstimatedDifficulty(description, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create task dependency');

                console.log(response.data)
                state.addDifficulty = response.data.difficulty;
                state.updateDifficulty = response.data.difficulty;
            } catch (error) {
                console.error(error);
            }
        },

        /*Project Services*/
        async fetchProjects(state) {
            try {
                const response = await getAllProjects(localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get projects');
                state.projects = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeProject(state, projectId) {
            try {
                const response = await deleteProject(projectId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete project');

                state.projects = state.projects.filter(project => project.id !== projectId);
            } catch (error) {
                console.error(error);
            }
        },
        async createProject(state, project) {
            console.log(project);
            try {
                const dateObject = parseISO(project.deadline);
                project.deadline = format(dateObject, "yyyy-MM-dd'T'HH:mm:ss.SSSxxx");
                /*TODO create user project connection
                project.userId = localStorage.getItem("userId")*/

                const response = await createProject(project, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create project');

                state.projects.push(response.data);
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

                const response = await updateProject(project, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update project');

                state.projects = state.projects.map(e => e.id === project.id ? response.data : e);

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
                const response = await updateTask(taskAndProject.task, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to update task');

                const response2 = await getTaskByProjectId(state.project.id, localStorage.getItem("token"));
                checkAuth(response2);
                if (response2.status !== 200) throw new Error('Failed to get tasks by projectId');
                state.projectTasks = response2.data;


            } catch (error) {
                console.error(error);
            }
        },
        async fetchUsersForProject(state, projectId) {
            try {
                const response = await getUserByProjectId(projectId, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to get users by projectId');

                state.projectUsers = response.data;
            } catch (error) {
                console.error(error);
            }
        },
        async removeProjectUser(state, projectUser) {
            try {
                const response = await deleteProjectUser(projectUser.projectId, state.currentUser.id, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to delete project user connection');

                const response2 = await getUserByProjectId(projectUser.projectId, localStorage.getItem("token"));
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
                const response = await createProjectUser(projectUser, localStorage.getItem("token"));

                checkAuth(response);

                if (response.status !== 200) throw new Error('Failed to create project user connection');

                const response2 = await getUserByProjectId(projectUser.projectId, localStorage.getItem("token"));
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
});

import {createVuetify} from 'vuetify';
import 'vuetify/dist/vuetify.min.css'; // Make sure this line is here to import Vuetify styles
import './index.scss'; // Ensure this line is here to import your custom styles
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
