var path = require('path');

module.exports = {
    context: path.resolve(__dirname, 'src/main/react'),
    entry:{
        main: './index.js'
    },
    devtool: '#source-map',
    cache: false,
    output: {
        path: __dirname,
        filename: './src/main/webapp/js/react/bundle.js'
    },
    mode: 'none',
    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query : {
                    presets: ['@babel/preset-env', '@babel/preset-react']
                }
            },
            {
                test: /\.css$/,
                use: ['style-loader', 'css-loader']
            }]
    }
};