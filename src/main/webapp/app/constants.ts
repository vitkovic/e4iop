// These constants are injected via webpack environment variables.
// You can add more variables in webpack.common.js or in profile specific webpack.<dev|prod>.js files.
// If you change the values in the webpack config files, you need to re run webpack to update the application

export const VERSION = process.env.VERSION;
// export const DEBUG_INFO_ENABLED: boolean = !!process.env.DEBUG_INFO_ENABLED;
export const SERVER_API_URL = process.env.SERVER_API_URL;
export const BUILD_TIMESTAMP = process.env.BUILD_TIMESTAMP;

// Errors
export const PROBLEM_BASE_URL = 'https://www.jhipster.tech/problem';
export const EMAIL_ALREADY_USED_TYPE = PROBLEM_BASE_URL + '/email-already-used';
export const LOGIN_ALREADY_USED_TYPE = PROBLEM_BASE_URL + '/login-already-used';
export const REGISTRATION_NUMBER_ALREADY_EXISTS_TYPE = PROBLEM_BASE_URL + '/registration_number-already-exists';
export const PIB_ALREADY_EXISTS_TYPE = PROBLEM_BASE_URL + '/pib-already-exists';
export const NIO_MUST_BE_UNIQUE_TYPE = PROBLEM_BASE_URL + '/ripo-already-exists-for-nio';
export const PUO_MUST_BE_UNIQUE_TYPE = PROBLEM_BASE_URL + '/ripo-already-exists-for-puo';
export const NOT_AUTHORIZED = PROBLEM_BASE_URL + '/not-authorized';
export const RI_ORGANIZATION_MUST_EXIST_TYPE = PROBLEM_BASE_URL + '/ri-must-exist';
export const INFRASTRUCTURE_IMAGES_LIMIT = PROBLEM_BASE_URL + '/infrastructure-image-limit';
