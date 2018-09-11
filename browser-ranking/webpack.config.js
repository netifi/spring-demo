const path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: path.resolve(__dirname, './src/main/js/index.js'),
    output: {
        path: path.resolve(__dirname, './src/main/resources/web/public/'),
        filename: 'app.js',
    },
    plugins: [
        new webpack.DefinePlugin({
            '__DEV__': true,
            '__WS_URL__': JSON.stringify(process.env.WS_URL || 'ws://localhost:8101/'),
        }),
    ],
    module: {
        loaders: [
            {
                test: /\.js?$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
            },
        ],
    },
};
