var path = require('path');
var webpack = require("webpack");

module.exports = {

    entry: [
        './client/main.js'
    ],

    devtool: "inline-source-map",

    output: {
        path: __dirname,
        filename: '../webapp/bundle.js'
    },
    module: {
        loaders: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    cacheDirectory: true,
                    presets: ['es2015', 'react']
                }
            },
            {
                test: /\.css$/,
                use: [
                    { loader: "style-loader" },
                    { loader: "css-loader" }
                ]
            }
        ]

    }

};