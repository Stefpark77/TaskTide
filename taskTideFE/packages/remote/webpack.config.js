const HtmlWebPackPlugin = require("html-webpack-plugin");
const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const { VueLoaderPlugin } = require("vue-loader");
const Dotenv = require('dotenv-webpack');
module.exports = (_, argv) => ({
  output: {
    publicPath: "http://localhost:8101/",
  },

  resolve: {
    extensions: [".tsx", ".ts", ".vue", ".jsx", ".js", ".json"],
  },

  devServer: {
    port: 8101,
    historyApiFallback: true,
  },

  module: {
    rules: [
      {
        test: /\.vue$/,
        loader: "vue-loader",
      },
      {
        test: /\.tsx?$/,
        use: [
          "babel-loader",
          {
            loader: "ts-loader",
            options: {
              transpileOnly: true,
              appendTsSuffixTo: ["\\.vue$"],
              happyPackMode: true,
            },
          },
        ],
      },
      {
        test: /\.(css|s[ac]ss)$/i,
        use: ["style-loader", "css-loader", "postcss-loader"],
      },
    ],
  },

  plugins: [
    new VueLoaderPlugin(),
    new ModuleFederationPlugin({
      name: "remote",
      filename: "remoteEntry.js",
      remotes: {},
      exposes: {
        "./Login": "./src/components/auth/Login.vue",
        "./SignUp": "./src/components/auth/SignUp.vue",
        "./Events": "./src/components/events/Events.vue",
        "./Menu": "./src/components/Menu.vue",
        "./AddEvent": "./src/components/events/AddEvent.vue",
        "./UpdateEvent": "./src/components/events/UpdateEvent.vue",
        "./Tasks": "./src/components/tasks/Tasks.vue",
        "./AddTask": "./src/components/tasks/AddTask.vue",
        "./UpdateTask": "./src/components/tasks/UpdateTask.vue",
        "./TaskPage": "./src/components/tasks/TaskPage.vue",
        "./Projects": "./src/components/projects/Projects.vue",
        "./AddProject": "./src/components/projects/AddProject.vue",
        "./UpdateProject": "./src/components/projects/UpdateProject.vue",
        "./ProjectPage": "./src/components/projects/ProjectPage.vue",
      },
      shared: require("./package.json").dependencies,
    }),
    new HtmlWebPackPlugin({
      template: "./src/index.html",
    }),
    new Dotenv()
  ],
});
