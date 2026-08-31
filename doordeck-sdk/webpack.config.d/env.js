const webpack = require("webpack");

config.plugins = config.plugins || [];
config.plugins.push(
    new webpack.DefinePlugin({
        "process.env": JSON.stringify({
            TEST_MAIN_USER_PASSWORD: process.env.TEST_MAIN_USER_PASSWORD ?? null,
            TEST_MAIN_USER_PRIVATE_KEY: process.env.TEST_MAIN_USER_PRIVATE_KEY ?? null,
            FUSION_INTEGRATIONS: process.env.FUSION_INTEGRATIONS ?? null,
            TEST_ENV_VAR: process.env.TEST_ENV_VAR ?? null
        }),
    })
);