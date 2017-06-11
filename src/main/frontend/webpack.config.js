var path = require('path');

module.exports = {
    entry: './client/main.js',
    devtool: 'sourcemaps',
    cache: true,
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
            }
        ]
    
    },

    devServer: {
        port: 9090,
        proxy: {
            '/*': {
                target: 'http://localhost:8080',
                secure: false,
                // <a href="https://github.com/nodejitsu/node-http-proxy">node-http-proxy</a> option - don't add /localhost:8080/ to proxied request paths
                prependPath: false
            },
        },
        publicPath: 'http://localhost:9090/'
    }
    
};