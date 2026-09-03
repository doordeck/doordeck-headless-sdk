// Passes the test environment variables to the browser that runs the Karma tests.
const NAMES = [
    "TEST_MAIN_USER_PASSWORD",
    "TEST_MAIN_USER_PRIVATE_KEY",
    "FUSION_INTEGRATIONS",
    "TEST_ENV_VAR"
];

// Unset variables are left out entirely so the tests fall back to their own defaults
const env = {};
for (const name of NAMES) {
    const value = process.env[name];
    if (value !== undefined) {
        env[name] = value;
    }
}

config.client = config.client || {};
config.client.env = env;
