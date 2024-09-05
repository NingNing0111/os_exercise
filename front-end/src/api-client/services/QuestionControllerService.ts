/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseResponseIPageQuestionVO } from '../models/BaseResponseIPageQuestionVO';
import type { BaseResponseListQuestionKnowledgeVO } from '../models/BaseResponseListQuestionKnowledgeVO';
import type { BaseResponseListQuestionTypeVO } from '../models/BaseResponseListQuestionTypeVO';
import type { BaseResponseQuestionVO } from '../models/BaseResponseQuestionVO';
import type { BaseResponseString } from '../models/BaseResponseString';
import type { UpdateQuestion } from '../models/UpdateQuestion';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class QuestionControllerService {
    /**
     * @param requestBody
     * @returns BaseResponseString OK
     * @throws ApiError
     */
    public static updateQuestion(
        requestBody: UpdateQuestion,
    ): CancelablePromise<BaseResponseString> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/question/',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param questionId
     * @returns BaseResponseQuestionVO OK
     * @throws ApiError
     */
    public static questionVoById(
        questionId: number,
    ): CancelablePromise<BaseResponseQuestionVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/question/{questionId}',
            path: {
                'questionId': questionId,
            },
        });
    }
    /**
     * @param current
     * @param pageSize
     * @param type
     * @param knowledge
     * @returns BaseResponseIPageQuestionVO OK
     * @throws ApiError
     */
    public static questionVoPage(
        current: number,
        pageSize: number,
        type?: string,
        knowledge?: string,
    ): CancelablePromise<BaseResponseIPageQuestionVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/question/{current}/{pageSize}',
            path: {
                'current': current,
                'pageSize': pageSize,
            },
            query: {
                'type': type,
                'knowledge': knowledge,
            },
        });
    }
    /**
     * @returns BaseResponseListQuestionTypeVO OK
     * @throws ApiError
     */
    public static types(): CancelablePromise<BaseResponseListQuestionTypeVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/question/types',
        });
    }
    /**
     * @returns BaseResponseListQuestionKnowledgeVO OK
     * @throws ApiError
     */
    public static knowledge(): CancelablePromise<BaseResponseListQuestionKnowledgeVO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/question/knowledge',
        });
    }
}
