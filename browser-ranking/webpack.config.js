var webpack = require('webpack');
var path = require('path');
var loaders = require('./webpack.loaders');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

loaders.push({
  test: /\.scss$/,
  loader: ExtractTextPlugin.extract({fallback: 'style-loader', use : 'css-loader?sourceMap&localIdentName=[local]___[hash:base64:5]!sass-loader?outputStyle=expanded'}),
  exclude: ['node_modules']
});

module.exports = {
  entry: [
    './src/main/js/app.js',
    './src/main/js/styles/main.scss'
  ],
  output: {
    publicPath: './',
    path: path.join(__dirname, './build/resources/main'),
    filename: '[chunkhash].js'
  },
  resolve: {
    extensions: ['.js'],

  },
  module: {
    loaders,
    // rules: [
    //     {
    //       test: /\.css$/,
    //       use: ['style-loader', 'css-loader']
    //     },
    //     {
    //       test: /\.scss$/,
    //       loader: ExtractTextPlugin.extract({fallback: 'style-loader', use : 'css-loader?sourceMap&localIdentName=[local]___[hash:base64:5]!sass-loader?outputStyle=expanded'}),
    //       exclude: ['node_modules']
    //     },
    //     {
    //       test: /\.(png|svg|jpg|gif)$/,
    //       use: ['file-loader']
    //     },
    //     {
    //       test: /\.(woff|woff2|eot|ttf|otf)$/,
    //       use: ['file-loader']
    //     }
    // ]
  },
  devServer: {
    contentBase: path.join(__dirname, 'build/resources/main'),
    compress: true,
    port: 9000
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    new webpack.optimize.OccurrenceOrderPlugin(),
    new ExtractTextPlugin({
      filename: '[contenthash].style.css',
      allChunks: true
    }),
    new HtmlWebpackPlugin({
      template: 'html-loader?interpolate!./src/main/js/index.html',
      files: {
        css: ['style.css'],
        js: ['bundle.js'],
      }
    })
  ]
};
