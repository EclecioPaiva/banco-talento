package com.projeto.service;

import java.util.HashMap;
import java.util.Collection;
import java.util.UUID;

public class TalentService {
    private HashMap<String, ModelTalento> modelsDb = new HashMap<>();

    public TalentService() {
        // Mock data
        modelsDb.put("1", new ModelTalento("1", "Talento 1"));
        modelsDb.put("2", new ModelTalento("2", "Talento 2"));
        modelsDb.put("3", new ModelTalento("3", "Talento 3"));
    }

    public Collection<ModelTalento> getAll() {
        return modelsDb.values();
    }

    public ModelTalento getById(String id) {
        return modelsDb.get(id);
    }

    public boolean  containsKey(String id) {
        return modelsDb.containsKey(id);
    }

    public ModelTalento addNew(String name) {
        String id = UUID.randomUUID().toString();
        ModelTalento newTalent = new ModelTalento(id, name);
        modelsDb.put(id, newTalent);
        return newTalent;
    }

    public ModelTalento update(String id, String name) {
        ModelTalento existingTalent = modelsDb.get(id);
        if (existingTalent != null) {
            existingTalent.nome = name;
        }
        return existingTalent;
    }


    public ModelTalento delete(String id) {
        return modelsDb.remove(id);
    }
}
