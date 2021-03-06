/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmc.justdoit.smartkanban.kanban.decoder;

import com.bmc.justdoit.smartkanban.api.objects.KanbanDecoderRequest;
import com.bmc.justdoit.smartkanban.kanban.queue.KanbanQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author gokumar
 */
public class KanbanDecoderQueueProcessor extends HttpServlet implements Runnable {

    @Override
    public void init() throws ServletException {
        System.out.println("Initializing Kanban Decoder Queue");
        new Thread(new KanbanDecoderQueueProcessor()).start();
    }

    public void run() {
        while (true) {
            try {
                KanbanDecoderRequest request = (KanbanDecoderRequest) KanbanQueue.DECODER_QUEUE.poll();
                if (request != null) {
                    System.out.println("Got a request to process Kanban.");
                    System.out.println("Request Id: " + request.getRequestId());
                    KanbanDecoder kanbanDecoder = new KanbanDecoder(request);
                    Thread th = new Thread(kanbanDecoder);
                    th.start();
                }
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(KanbanDecoderQueueProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
