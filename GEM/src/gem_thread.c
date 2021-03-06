#include "gem_thread.h"

void gemThreadQInsert(GemThread ** queue, GemThread * node) {
    if (*queue) {
        GemThread * next, * prev;
        next = (*queue);
        prev = (*queue)->prev;
        // Forward links
        node->next = next;
        next->prev = node;
        // Back links
        node->prev = prev;
        prev->next = node;
    }
    else {
        node->prev = node;
        node->next = node;
    }
    // Update head
    *queue = node;
}


void gemThreadQRemove(GemThread ** queue, GemThread * node) {
    if (node->next == node) {
       *queue = 0;
    }
    else {
        node->next->prev = node->prev;
        node->prev->next = node->next;
        if (*queue == node) *queue = node->next;
    }
    node->prev = 0;
    node->next = 0;
}

void gemThreadQMoveHead(GemThread ** qSrc, GemThread ** qDest) {
    GemThread * node = *qSrc;
    if (node) {
        gemThreadQRemove(qSrc, node);
        gemThreadQInsert(qDest, node);
    }
}

