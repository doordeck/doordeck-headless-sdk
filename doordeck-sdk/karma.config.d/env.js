// karma.config.d/env.js
const fs = require("fs");
const os = require("os");
const path = require("path");

const names = [
    "TEST_MAIN_USER_PASSWORD",
    "TEST_MAIN_USER_PRIVATE_KEY",
    "FUSION_INTEGRATIONS",
    "TEST_ENV_VAR"
];

const env = {};
for (const name of names) {
    env[name] = process.env[name] ?? null;
}

const shim = path.join(os.tmpdir(), "kotlin-test-env.js");
fs.writeFileSync(
    shim,
    `globalThis.process = globalThis.process || {};
     globalThis.process.env = Object.assign(globalThis.process.env || {}, ${JSON.stringify(env)});`
);

config.files.unshift({ pattern: shim, included: true, served: true, watched: false });